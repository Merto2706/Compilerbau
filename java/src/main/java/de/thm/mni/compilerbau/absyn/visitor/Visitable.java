package de.thm.mni.compilerbau.absyn.visitor;

/**
 * This class is required to implement the visitor pattern.
 */
public interface Visitable {

    void accept(Visitor visitor);

}
