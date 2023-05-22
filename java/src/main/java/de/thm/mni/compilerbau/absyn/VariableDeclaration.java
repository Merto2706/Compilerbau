package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;
import de.thm.mni.compilerbau.table.Identifier;

/**
 * This class represents the local declaration of a variable.
 * <p>
 * Variables are declared inside a procedure and combine an {@link Identifier} with a {@link TypeExpression},
 * expressing the variables type.
 * Variables are only visible in the local scope of their procedure.
 */
public class VariableDeclaration extends Node {
    public final Identifier name;
    public final TypeExpression typeExpression;

    /**
     * Creates a new node representing the declaration of a local variable in a procedures body.
     *
     * @param position       The position of the declaration in the source code.
     * @param name           The identifier of the declared local variable.
     * @param typeExpression The type expression used to express the type of the local variable.
     */
    public VariableDeclaration(Position position, Identifier name, TypeExpression typeExpression) {
        super(position);
        this.name = name;
        this.typeExpression = typeExpression;
    }

    @Override
    public String toString() {
        return formatAst("VariableDeclaration", name, typeExpression);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
