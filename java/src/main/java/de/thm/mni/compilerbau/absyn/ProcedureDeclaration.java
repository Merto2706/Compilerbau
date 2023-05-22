package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;
import de.thm.mni.compilerbau.table.Identifier;

import java.util.List;

/**
 * This class represents the declaration of a procedure in SPL.
 * <p>
 * When declaring a procedure, you have to provide a name, which is used as an identifier in this declaration.
 * Additionally a declaration of a procedure, declares its parameters as a list, a list of local variables and
 * a list of statements in the body of the procedure.
 */
final public class ProcedureDeclaration extends GlobalDeclaration {
    /**
     * This list represents the parameters of the procedure.
     */
    public final List<ParameterDeclaration> parameters;
    /**
     * This list represents the local variables of the procedure.
     */
    public final List<VariableDeclaration> variables;
    /**
     * This list holds the statements contained in the procedures body.
     */
    public final List<Statement> body;

    /**
     * Creates a new node representing a procedure declaration.
     *
     * @param position   The position of the procedure in the source code.
     * @param name       The procedures identifier.
     * @param parameters The procedures parameter list.
     * @param variables  The procedures local variables.
     * @param body       The statements in the procedures body.
     */
    public ProcedureDeclaration(Position position, Identifier name, List<ParameterDeclaration> parameters, List<VariableDeclaration> variables, List<Statement> body) {
        super(position, name);
        this.parameters = parameters;
        this.variables = variables;
        this.body = body;
    }

    @Override
    public String toString() {
        return formatAst("ProcedureDeclaration",
                name,
                formatAst("Parameters", parameters.toArray()),
                formatAst("Variables", variables.toArray()),
                formatAst("Body", body.toArray())
        );
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
