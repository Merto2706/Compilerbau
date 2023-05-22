package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;
import de.thm.mni.compilerbau.table.Identifier;

import java.util.List;

/**
 * This class represents the call of another procedure in SPL.
 * <p>
 * Whenever a procedure is called, the name of the procedure has to be provided and additionally a list of expressions
 * whose types match the types of the procedures parameters.
 * All arguments of the call are evaluated and passed to the called procedure which is then executed.
 * The execution of the current procedure is halted until the called procedure returns.
 */
public non-sealed class CallStatement extends Statement {
    public final Identifier procedureName;
    public final List<Expression> arguments;

    /**
     * Creates a new node representing a procedure call.
     *
     * @param position      The position of the call in the source code.
     * @param procedureName The identifier of the called procedure.
     * @param arguments     The list of expressions, whose values will be passed to the procedure.
     */
    public CallStatement(Position position, Identifier procedureName, List<Expression> arguments) {
        super(position);
        this.procedureName = procedureName;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return formatAst("CallStatement", procedureName, formatAst("Arguments", arguments.toArray()));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
