package de.thm.mni.compilerbau.table;

import de.thm.mni.compilerbau.types.Type;

/**
 * Contains the information about a parameter, that are necessary when calling the associated procedure.
 */
public class ParameterType {
    public final Type type;
    public final boolean isReference;
    public Integer offset = null; // This value has to be set in phase 5

    /**
     * @param type        The semantic type of the parameter. See {@link Type} and its subclasses.
     * @param isReference If the parameter is a reference parameter.
     */
    public ParameterType(Type type, boolean isReference) {
        this.type = type;
        this.isReference = isReference;
    }

    /**
     * @param type        The semantic type of the parameter. See {@link Type} and its subclasses.
     * @param isReference If the parameter is a reference parameter.
     * @param offset      The stack offset of this parameter when calling the associated procedure in respect to the stack pointer
     */
    public ParameterType(Type type, boolean isReference, int offset) {
        this(type, isReference);
        this.offset = offset;
    }

    @Override
    public String toString() {
        return String.format("%s%s", isReference ? "ref " : "", this.type);
    }
}
