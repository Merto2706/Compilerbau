package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

/**
 * This class represents a variable that is used as an {@link Expression}.
 * <p>
 * Example: 3 * i<br>
 * In this example, the named variable 'i' is used as the right operand of the arithmetic expression.
 * When using a {@link VariableExpression}, the value that a variable holds is requested instead of its address.
 * The semantic type of a {@link VariableExpression} is the same as the type of its contained {@link Variable}.
 */
public final class VariableExpression extends Expression {
    public final Variable variable;

    /**
     * Creates a new node representing the value of a variable.
     *
     * @param position The position of the variable in the source code.
     * @param variable The variable whose value is used as a value for this expression.
     */
    public VariableExpression(Position position, Variable variable) {
        super(position);
        this.variable = variable;
    }

    @Override
    public String toString() {
        return formatAst("VariableExpression", variable);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
