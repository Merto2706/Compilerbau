package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

/**
 * This class represents a type expression, for the type of a fixed-size array of another type.
 * Example: array [ 10 ] of int
 * <p>
 * In this example, the base type of this expression, is the {@link NamedTypeExpression} with "int" as identifier.
 * The size of this array is defined by the literal 10.
 */
public final class ArrayTypeExpression extends TypeExpression {
    public final TypeExpression baseType;
    public final int arraySize;

    /**
     * Creates a new node representing a type expression for the type of a fixed-size array.
     *
     * @param position  The position of the type expression in the source code.
     * @param arraySize The number of elements an array of this type can hold.
     * @param baseType  The type expression of the elements type.
     */
    public ArrayTypeExpression(Position position, int arraySize, TypeExpression baseType) {
        super(position);
        this.baseType = baseType;
        this.arraySize = arraySize;
    }

    @Override
    public String toString() {
        return formatAst("ArrayTypeExpression", baseType, arraySize);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
