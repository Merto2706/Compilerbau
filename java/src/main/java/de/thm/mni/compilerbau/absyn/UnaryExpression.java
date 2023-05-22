package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

import java.util.List;
import java.util.Map;

/**
 * This class represents an expression with a unary operator.
 * Example: -x, !(a < b)
 * <p>
 * Unary expressions apply a unary operator to a single operand on the right side of the operator.
 * The type of the expression depends on the operator.
 */
public final class UnaryExpression extends Expression {
    public enum Operator {
        MINUS;

        public String operatorString() {
            return switch(this){
                case MINUS -> "-";
            };
        }
    }

    public final Operator operator;
    public final Expression operand;

    /**
     * Creates a new node representing an expression combining two expressions with an operator.
     *
     * @param position The position of the expression in the source code.
     * @param operator The operator used in this expression.
     * @param operand  The operand on the right hand side of the operator.
     */
    public UnaryExpression(Position position, Operator operator, Expression operand) {
        super(position);
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return formatAst("UnaryExpression", operator, operand);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
