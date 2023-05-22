package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

import java.util.List;
import java.util.Map;

/**
 * This class represents an expression, combining two expressions with an operator.
 * Example: 3 * i
 * <p>
 * Binary expressions always combine two expressions of the type integer with one of 10 possible operators.
 * The operator defines, how the left and the right expression are combined.
 * The semantic type of an expression is dependant of the operator.
 */
public final class BinaryExpression extends Expression {
    public enum Operator {
        ADD, // +
        SUB, // -
        MUL, // *
        DIV, // /
        EQU, // =
        NEQ, // #
        LST, // <
        LSE, // <=
        GRT, // >
        GRE; // >=

        /**
         * Checks whether the operator is an arithmetic operator.
         *
         * @return true if the operator is an arithmetic operator.
         */
        public boolean isArithmetic() {
            return List.of(ADD, SUB, MUL, DIV).contains(this);
        }

        /**
         * Checks whether the operator is an equality operator (= and #).
         *
         * @return true if the operator is an equality operator.
         */
        public boolean isEqualityOperator() {
            return List.of(NEQ, EQU).contains(this);
        }

        /**
         * Checks whether the operator is a comparison operator.
         *
         * @return true if the operator is a comparison operator.
         */
        public boolean isComparison() {
            return this.isEqualityOperator() || List.of(LSE, LST, GRT, GRE).contains(this);
        }

        /**
         * Flips the operator if it is a comparison operator
         *
         * @return The "opposite" comparison operator
         */
        public Operator flipComparison() {
            return Map.of(EQU, NEQ, NEQ, EQU, LST, GRE, GRE, LST, LSE, GRT, GRT, LSE).get(this);
        }

        public String operatorString() {
            return switch (this) {
                case ADD -> "+";
                case SUB -> "-";
                case MUL -> "*";
                case DIV -> "/";
                case EQU -> "=";
                case NEQ -> "#";
                case LST -> "<";
                case LSE -> "<=";
                case GRT -> ">";
                case GRE -> ">=";
            };
        }
    }

    public final Operator operator;
    public final Expression leftOperand;
    public final Expression rightOperand;

    /**
     * Creates a new node representing an expression combining two expressions with an operator.
     *
     * @param position     The position of the expression in the source code.
     * @param operator     The operator used in this expression.
     * @param leftOperand  The operand on the left hand side of the operator.
     * @param rightOperand The operand on the right hand side of the operator.
     */
    public BinaryExpression(Position position, Operator operator, Expression leftOperand, Expression rightOperand) {
        super(position);
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public String toString() {
        return formatAst("BinaryExpression", operator, leftOperand, rightOperand);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
