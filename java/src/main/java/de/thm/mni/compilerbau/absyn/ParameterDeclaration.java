package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;
import de.thm.mni.compilerbau.table.Identifier;

/**
 * This class represents the local declaration of a parameter in SPL.
 * <p>
 * Parameters are declared in the parameter list of a procedure. They combine an {@link Identifier} with
 * a {@link TypeExpression}, expressing the parameters type and additionally have to store
 * whether the parameter is passed as a reference.
 * Parameters are only visible in the local scope of their procedure.
 */
public class ParameterDeclaration extends Node {
    public final Identifier name;
    public final TypeExpression typeExpression;
    public final boolean isReference;

    /**
     * Creates a new node representing the declaration of a parameter in the head of a procedure.
     *
     * @param position       The position of the declaration in the source code.
     * @param name           The identifier of the declared parameter.
     * @param typeExpression The type expression used to express the parameters type.
     * @param isReference    A boolean value used to represent whether the parameter is passed as a reference.
     */
    public ParameterDeclaration(Position position, Identifier name, TypeExpression typeExpression, boolean isReference) {
        super(position);
        this.name = name;
        this.typeExpression = typeExpression;
        this.isReference = isReference;
    }

    @Override
    public String toString() {
        return formatAst("ParameterDeclaration", name, typeExpression, isReference);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
