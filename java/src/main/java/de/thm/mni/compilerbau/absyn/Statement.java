package de.thm.mni.compilerbau.absyn;

/**
 * This class is the abstract superclass of every statement in SPL.
 * <p>
 * There exist many different statements present in SPL, which may all occur in the body of a procedure.
 */
public abstract sealed class Statement extends Node
        permits AssignStatement, CallStatement, CompoundStatement, EmptyStatement, IfStatement, WhileStatement {
    public Statement(Position position) {
        super(position);
    }
}
