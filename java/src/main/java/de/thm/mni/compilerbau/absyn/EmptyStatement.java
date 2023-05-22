package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

/**
 * This class represents a statement without any effect.
 */
public final class EmptyStatement extends Statement {
    /**
     * Creates a new node representing an empty statement.
     *
     * @param position The position of the statement in the source code.
     */
    public EmptyStatement(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return formatAst("EmptyStatement");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
