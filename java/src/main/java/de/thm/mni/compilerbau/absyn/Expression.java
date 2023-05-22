package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.types.Type;

/**
 * This class is the abstract superclass of all expressions in SPL.
 * <p>
 * Everything that behaves like a value is an {@link Expression} in SPL.
 * There are three types of expressions: {@link BinaryExpression}, {@link IntLiteral} and {@link VariableExpression}
 * <p>
 * Every expression has a semantic type, which has to be calculated in phase 4.
 */
sealed public abstract class Expression extends Node permits BinaryExpression, IntLiteral, UnaryExpression, VariableExpression {
    public Type dataType = null;

    public Expression(Position position) {
        super(position);
    }
}
