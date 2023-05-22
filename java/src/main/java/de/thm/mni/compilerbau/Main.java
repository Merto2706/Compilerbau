package de.thm.mni.compilerbau;

import de.thm.mni.compilerbau.absyn.Program;
import de.thm.mni.compilerbau.phases._01_scanner.Scanner;
import de.thm.mni.compilerbau.phases._02_03_parser.Parser;
import de.thm.mni.compilerbau.phases._02_03_parser.Sym;
import de.thm.mni.compilerbau.phases._04a_tablebuild.TableBuilder;
import de.thm.mni.compilerbau.phases._04b_semant.ProcedureBodyChecker;
import de.thm.mni.compilerbau.phases._05_varalloc.VarAllocator;
import de.thm.mni.compilerbau.phases._06_codegen.CodeGenerator;
import de.thm.mni.compilerbau.table.Identifier;
import de.thm.mni.compilerbau.utils.SplError;
import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.System.exit;

class Main {

    /**
     * CUP encourages you to use {@link java_cup.runtime.ComplexSymbolFactory} as a {@link SymbolFactory} which we
     * don't need. The default implementation provides more than enough information for our needs.
     */
    @SuppressWarnings("deprecation")
    private static final SymbolFactory symbolFactory = new DefaultSymbolFactory();

    /**
     * Prints a token to stdout to realize the --tokens output.
     *
     * @param token The token to print.
     */
    private static void showToken(Symbol token) {
        System.out.printf("TOKEN = %s", Sym.terminalNames[token.sym]);   // Name of token class

        if (token.sym != Sym.EOF) System.out.printf(" in line %d, column %d", token.left, token.right); // Line and Column

        if (token.value != null) {
            System.out.print(", value = ");
            if (token.value instanceof String || token.value instanceof Identifier) System.out.printf("\"%s\"", token.value);
            else System.out.print(token.value);
        }
        System.out.println();
    }


    public static void main(String[] args) {
        CommandLineOptions options = CommandLineOptions.parse(args);

        try (FileReader input = new FileReader(options.inFilename)) {
            Scanner scanner = new Scanner(input);
            scanner.options = options; // Inject the command line options into the scanner to grant it access to feature flags.

            if (options.phaseOption == CommandLineOptions.PhaseOption.TOKENS) {
                Symbol token;
                do {
                    token = scanner.next_token();
                    showToken(token);
                } while (token.sym != Sym.EOF);
                exit(0);
            }

            //Parse errors are caught by the below exception handler
            Parser parser = new Parser(scanner, symbolFactory);
            parser.options = options; // Inject the command line options into the parser to grant it access to feature flags.
            Program program = (Program) parser.parse().value; // Change 'parse' to 'debug_parse' for detailed parsing output. Don't forget to change it back

            if (options.phaseOption == CommandLineOptions.PhaseOption.PARSE) {
                System.out.println("Input parsed successfully!");
                exit(0);
            }

            if (options.phaseOption == CommandLineOptions.PhaseOption.ABSYN) {
                System.out.println(program);
                exit(0);
            }

            final var table = new TableBuilder(options).buildSymbolTable(program);
            if (options.phaseOption == CommandLineOptions.PhaseOption.TABLES) exit(0);

            new ProcedureBodyChecker(options).checkProcedures(program, table);
            if (options.phaseOption == CommandLineOptions.PhaseOption.SEMANT) {
                System.out.println("No semantic errors found!");
                exit(0);
            }

            new VarAllocator(options).allocVars(program, table);
            if (options.phaseOption == CommandLineOptions.PhaseOption.VARS) exit(0);

            try (PrintWriter out = options.getOutputWriter()) {
                new CodeGenerator(options, out).generateCode(program, table);
            } catch (IOException e) {
                System.err.printf("An error occurred: Cannot open output file '%s'\n", options.outFilename);
                exit(1);
            }
        } catch (FileNotFoundException e) {
            System.err.printf("An error occurred: Cannot open input file '%s'\n", options.inFilename);
            exit(1);
        } catch (SplError error) {
            System.err.println("An error occurred:");
            if (error.position.line >= 0) System.err.printf("Line %d, Column %d: ", error.position.line, error.position.column);
            System.err.println(error.getMessage());
            exit(error.errorCode);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
            exit(1);
        }
    }
}
