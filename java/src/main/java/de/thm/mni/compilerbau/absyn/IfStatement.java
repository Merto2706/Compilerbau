package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

/**
 * This class represents an if-statement in SPL.
 * <p>
 * An if-statement consists of two branches and an expression as condition. Which of the two branches
 * is executed depends on the value of the boolean-typed condition.
 */
public final class IfStatement extends Statement {
    public final Expression condition;
    public final Statement thenPart;
    public final Statement elsePart;

    /**
     * Creates a new node representing an if-statement.
     *
     * @param position  The position of the statement in the source code.
     * @param condition The expression deciding which branch to execute.
     * @param thenPart  The executed statement if the expression evaluates to true.
     * @param elsePart  The executed statement if the expression evaluates to false.
     */
    public IfStatement(Position position, Expression condition, Statement thenPart, Statement elsePart) {
        super(position);
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    @Override
    public String toString() {
        return formatAst("IfStatement", condition, thenPart, elsePart);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
