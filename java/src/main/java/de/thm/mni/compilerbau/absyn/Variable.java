package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.types.Type;

/**
 * This class is the abstract superclass of any variable in an SPL program.
 * <p>
 * A variable is either a simple named variable ({@link NamedVariable}) or an array access ({@link ArrayAccess}).
 * Every variable has a semantic {@link Type} which has to be filled in during phase 4.
 */
public abstract sealed class Variable extends Node permits ArrayAccess, NamedVariable{
    public Type dataType = null;

    public Variable(Position position) {
        super(position);
    }
}
