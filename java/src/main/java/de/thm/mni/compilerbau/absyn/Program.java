package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

import java.util.List;

/**
 * This class represents the root of the AST.
 * <p>
 * A program in SPL consists of a list of global declarations ({@link TypeDeclaration} and {@link ProcedureDeclaration}).
 */
public class Program extends Node {
    public final List<GlobalDeclaration> declarations;

    /**
     * Creates a new node representing the entire SPL program.
     *
     * @param position     The position of the SPL program in the source code. (This is usually the position of the first declaration)
     * @param declarations The list of global declarations in the SPL program.
     */
    public Program(Position position, List<GlobalDeclaration> declarations) {
        super(position);
        this.declarations = declarations;
    }

    @Override
    public String toString() {
        return formatAst("Program", declarations.toArray());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
