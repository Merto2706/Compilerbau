package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

/**
 * This class represents a while-statement in SPL.
 * <p>
 * A while-statement consists of a condition expression and a body. The body is repeatedly executed until the condition's
 * value becomes false.
 */
public final class WhileStatement extends Statement {
    public final Expression condition;
    public final Statement body;

    /**
     * Creates a new node representing a while-statement.
     *
     * @param position  The position of the statement in the source code.
     * @param condition The expression used to determine whether the while-loop should continue.
     * @param body      The statement executed until the condition evaluates to false.
     */
    public WhileStatement(Position position, Expression condition, Statement body) {
        super(position);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return formatAst("WhileStatement", condition, body);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
