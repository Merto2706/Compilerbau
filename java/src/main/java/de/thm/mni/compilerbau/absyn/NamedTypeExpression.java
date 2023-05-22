package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;
import de.thm.mni.compilerbau.table.Identifier;

/**
 * This class represents a type expression consisting only of an {@link Identifier}.
 * Example: int
 * <p>
 * In this example, "int" is the {@link Identifier} of this {@link NamedTypeExpression}.
 */
public final class NamedTypeExpression extends TypeExpression {
    public final Identifier name;

    /**
     * Creates a new node representing a named type expression.
     *
     * @param position The position of the type expression in the source code.
     * @param name     The identifier used to express the type.
     */
    public NamedTypeExpression(Position position, Identifier name) {
        super(position);
        this.name = name;
    }

    @Override
    public String toString() {
        return formatAst("NamedTypeExpression", name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
