package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

/**
 * This class represents an array access in SPL.
 * <p>
 * Example: vector[3]
 * In this example, vector is the accessed array and the literal 3 is the index of the access.
 */
public final class ArrayAccess extends Variable {
    public final Variable array;
    public final Expression index;

    /**
     * Creates a new node representing an array access.
     *
     * @param position The position of the array access in the source code.
     * @param array    The variable representing the accessed array.
     * @param index    The expression representing the index of the access.
     */
    public ArrayAccess(Position position, Variable array, Expression index) {
        super(position);
        this.array = array;
        this.index = index;
    }

    @Override
    public String toString() {
        return formatAst("ArrayAccess", array, index);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
