package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.table.Identifier;

/**
 * This class is the abstract superclass of every global declaration in SPL.
 * <p>
 * Global declarations are all declarations done in the global scope.
 * This declarations may either be a {@link TypeDeclaration} or a {@link ProcedureDeclaration}.
 */
sealed public abstract class GlobalDeclaration extends Node permits ProcedureDeclaration, TypeDeclaration {
    public final Identifier name;

    /**
     * Creates a new node representing a global declaration.
     *
     * @param position The global declarations position in the source code.
     * @param name     The identifier for this global declaration.
     */
    public GlobalDeclaration(Position position, Identifier name) {
        super(position);
        this.name = name;
    }
}
