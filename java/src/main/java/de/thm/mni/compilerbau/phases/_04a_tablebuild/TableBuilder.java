package de.thm.mni.compilerbau.phases._04a_tablebuild;

import de.thm.mni.compilerbau.CommandLineOptions;
import de.thm.mni.compilerbau.absyn.*;
import de.thm.mni.compilerbau.absyn.visitor.DoNothingVisitor;
import de.thm.mni.compilerbau.absyn.visitor.Visitor;
import de.thm.mni.compilerbau.table.*;
import de.thm.mni.compilerbau.types.ArrayType;
import de.thm.mni.compilerbau.types.Type;
import de.thm.mni.compilerbau.utils.SplError;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create and populate a {@link SymbolTable} containing entries for every symbol in the currently
 * compiled SPL program.
 * Every declaration of the SPL program needs its corresponding entry in the {@link SymbolTable}.
 * <p>
 * Calculated {@link Type}s can be stored in and read from the dataType field of the {@link Expression},
 * {@link TypeExpression} or {@link Variable} classes.
 */
public class TableBuilder {
    private final CommandLineOptions commandLineOptions;

    public TableBuilder(CommandLineOptions commandLineOptions) {
        this.commandLineOptions = commandLineOptions;
    }

    public SymbolTable buildSymbolTable(Program program) {
        SymbolTable symbolTable = TableInitializer.initializeGlobalTable(commandLineOptions);

        TableBuilderVisitor tableBuilderVisitor = new TableBuilderVisitor(symbolTable);
        tableBuilderVisitor.visit(program);

        Entry entry = symbolTable.lookup(new Identifier("main"), SplError.MainIsMissing());

        if (!(entry instanceof ProcedureEntry))
            throw SplError.MainIsNotAProcedure();

        if (!((ProcedureEntry) entry).parameterTypes.isEmpty())
            throw SplError.MainMustNotHaveParameters();

        return symbolTable;
    }

    /**
     * Prints the local symbol table of a procedure together with a heading-line
     * NOTE: You have to call this after completing the local table to support '--tables'.
     *
     * @param name  The name of the procedure
     * @param entry The entry of the procedure to print
     */
    private static void printSymbolTableAtEndOfProcedure(Identifier name, ProcedureEntry entry) {
        System.out.format("Symbol table at end of procedure '%s':\n", name);
        System.out.println(entry.localTable.toString());
    }

    private class TableBuilderVisitor extends DoNothingVisitor {
        SymbolTable table;
        TableBuilderVisitor upperVisitor;

        TableBuilderVisitor(SymbolTable table) {
            this.table = table;
        }

        TableBuilderVisitor(SymbolTable table, TableBuilderVisitor upperVisitor) {
            this.table = table;
            this.upperVisitor = upperVisitor;
        }

        @Override
        public void visit(Program program) {
            for (GlobalDeclaration declaration : program.declarations) {
                declaration.accept(this);
            }
        }

        @Override
        public void visit(TypeDeclaration typeDeclaration) {
            typeDeclaration.typeExpression.accept(this);
            Entry entry = new TypeEntry(typeDeclaration.typeExpression.dataType);
            table.enter(typeDeclaration.name, entry,
                    SplError.RedeclarationAsType(typeDeclaration.position, typeDeclaration.name));
        }

        @Override
        public void visit(NamedTypeExpression namedTypeExpression) {
            Entry entry = table.lookup(namedTypeExpression.name,
                    SplError.UndefinedType(namedTypeExpression.position, namedTypeExpression.name));
            if (!(entry instanceof TypeEntry))
                throw SplError.NotAType(namedTypeExpression.position, namedTypeExpression.name);

            namedTypeExpression.dataType = ((TypeEntry) entry).type;
        }

        @Override
        public void visit(ArrayTypeExpression arrayTypeExpression) {
            arrayTypeExpression.baseType.accept(this);
            arrayTypeExpression.dataType = new ArrayType(arrayTypeExpression.baseType.dataType,
                    arrayTypeExpression.arraySize);
        }

        @Override
        public void visit(ProcedureDeclaration procedureDeclaration) {
            List<ParameterType> paramTypes = new ArrayList<>();
            SymbolTable localTable = new SymbolTable(table);

            TableBuilderVisitor localVisitor = new TableBuilderVisitor(localTable, this);

            for (var parameterDeclaration : procedureDeclaration.parameters) {
                parameterDeclaration.accept(localVisitor);
                paramTypes.add(new ParameterType(parameterDeclaration.typeExpression.dataType, parameterDeclaration.isReference));
            }

            for (var var : procedureDeclaration.variables) {
                var.accept(localVisitor);
            }

            ProcedureEntry procedureEntry = new ProcedureEntry(localTable, paramTypes);
            table.enter(procedureDeclaration.name, procedureEntry, SplError.RedeclarationAsProcedure(procedureDeclaration.position, procedureDeclaration.name));
            if (commandLineOptions.phaseOption == CommandLineOptions.PhaseOption.TABLES)
                printSymbolTableAtEndOfProcedure(procedureDeclaration.name, procedureEntry);
        }

        @Override
        public void visit(ParameterDeclaration parameterDeclaration) {
            parameterDeclaration.typeExpression.accept(upperVisitor);

            VariableEntry entry = new VariableEntry(parameterDeclaration.typeExpression.dataType, parameterDeclaration.isReference);
            if (parameterDeclaration.typeExpression.dataType instanceof ArrayType && !parameterDeclaration.isReference)
                throw SplError.MustBeAReferenceParameter(parameterDeclaration.position, parameterDeclaration.name);

            table.enter(parameterDeclaration.name, entry, SplError.RedeclarationAsParameter(parameterDeclaration.position, parameterDeclaration.name));
        }

        @Override
        public void visit(VariableDeclaration variableDeclaration) {
            variableDeclaration.typeExpression.accept(this);

            VariableEntry entry = new VariableEntry(variableDeclaration.typeExpression.dataType, false);

            table.enter(variableDeclaration.name, entry, SplError.RedeclarationAsVariable(variableDeclaration.position, variableDeclaration.name));
        }
    }
}
