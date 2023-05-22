package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;
import de.thm.mni.compilerbau.table.Identifier;

/**
 * This class represents a named variable in SPL.
 * <p>
 * Example: <code>i := 4</code>
 * In this statement, i is used as a {@link NamedVariable}.
 * <p>
 * A named variable is identified by its name, which is an {@link Identifier}.
 */
public final class NamedVariable extends Variable {
    public final Identifier name;

    /**
     * Creates a new node representing a named variable.
     *
     * @param position The position of the variable in the source code.
     * @param name     The identifier of the variable.
     */
    public NamedVariable(Position position, Identifier name) {
        super(position);
        this.name = name;
    }

    @Override
    public String toString() {
        return formatAst("NamedVariable", name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
