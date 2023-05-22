package de.thm.mni.compilerbau.types;

/**
 * Represents array types in spl.
 * Is constructed each time an {@link de.thm.mni.compilerbau.absyn.ArrayTypeExpression} is encountered in the source code.
 */
public class ArrayType extends Type {
    public final Type baseType;
    public final int arraySize;

    /**
     * Creates a new {@link Type} representing a fixed-size array of another {@link Type}.
     *
     * @param baseType  The type of the array's elements.
     * @param arraySize The number of elements in an array of this type.
     */
    public ArrayType(Type baseType, int arraySize) {
        super(arraySize * baseType.byteSize);
        this.baseType = baseType;
        this.arraySize = arraySize;
    }

    @Override
    public String toString() {
        return String.format("array [%d] of %s", arraySize, baseType);
    }
}
