package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

/**
 * This class represents an assignment in SPL.
 * <p>
 * An assignment evaluates its right hand side expression and stores this value inside the variable
 * on the left hand side of the assignment operator.
 */
public final class AssignStatement extends Statement {
    public final Variable target;
    public final Expression value;

    /**
     * Creates a new node representing an assignment.
     *
     * @param position The position of the statement in the source code.
     * @param target   The variable where the value is assigned to.
     * @param value    The value to be assigned.
     */
    public AssignStatement(Position position, Variable target, Expression value) {
        super(position);
        this.target = target;
        this.value = value;
    }

    @Override
    public String toString() {
        return formatAst("AssignStatement", target, value);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
