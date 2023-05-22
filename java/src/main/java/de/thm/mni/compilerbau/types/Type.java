package de.thm.mni.compilerbau.types;

/**
 * Represents the semantic type of expressions and variables.
 * Not to be confused with {@link de.thm.mni.compilerbau.absyn.TypeExpression}.
 */
public abstract class Type {
    /**
     * The size in bytes this type uses in memory.
     * Example: 4 for 'int', 12 for 'array[3] of int'
     */
    public final int byteSize;

    Type(int byteSize) {
        this.byteSize = byteSize;
    }
}
