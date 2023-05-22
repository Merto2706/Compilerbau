package de.thm.mni.compilerbau.phases._04a_tablebuild;

import de.thm.mni.compilerbau.CommandLineOptions;
import de.thm.mni.compilerbau.absyn.Expression;
import de.thm.mni.compilerbau.absyn.Program;
import de.thm.mni.compilerbau.absyn.TypeExpression;
import de.thm.mni.compilerbau.absyn.Variable;
import de.thm.mni.compilerbau.table.Identifier;
import de.thm.mni.compilerbau.table.ProcedureEntry;
import de.thm.mni.compilerbau.table.SymbolTable;
import de.thm.mni.compilerbau.types.Type;
import de.thm.mni.compilerbau.utils.NotImplemented;

/**
 * This class is used to create and populate a {@link SymbolTable} containing entries for every symbol in the currently
 * compiled SPL program.
 * Every declaration of the SPL program needs its corresponding entry in the {@link SymbolTable}.
 * <p>
 * Calculated {@link Type}s can be stored in and read from the dataType field of the {@link Expression},
 * {@link TypeExpression} or {@link Variable} classes.
 */
public class TableBuilder {
    private final CommandLineOptions options;

    public TableBuilder(CommandLineOptions options) {
        this.options = options;
    }

    public SymbolTable buildSymbolTable(Program program) {
        //TODO (assignment 4a): Initialize a symbol table with all predefined symbols and fill it with user-defined symbols

        throw new NotImplemented();
    }

    /**
     * Prints the local symbol table of a procedure together with a heading-line
     * NOTE: You have to call this after completing the local table to support '--tables'.
     *
     * @param name  The name of the procedure
     * @param entry The entry of the procedure to print
     */
    static void printSymbolTableAtEndOfProcedure(Identifier name, ProcedureEntry entry) {
        System.out.format("Symbol table at end of procedure '%s':\n", name);
        System.out.println(entry.localTable.toString());
    }
}
