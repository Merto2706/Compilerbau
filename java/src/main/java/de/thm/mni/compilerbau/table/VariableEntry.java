package de.thm.mni.compilerbau.table;

import de.thm.mni.compilerbau.types.Type;

/**
 * Represents the table entry for variable- and parameter-declarations in SPL.
 * Please note that parameters of a procedure will also need to be entered as VariableEntries, there is no separate ParameterEntry.
 * Parameters also have an associated {@link ParameterType}, that needs to be added to the associated procedure's ProcedureEntry.
 */
public class VariableEntry implements Entry {
    public final Type type;
    public final boolean isReference;
    public Integer offset = null; // This value has to be set in phase 5

    /**
     * Creates a new {@link Entry} representing a declared SPL variable. This variable can be a local variable or the
     * parameter of a procedure.
     *
     * @param type        The semantic type of the variable. Calculated by looking at the respective type expression.
     * @param isReference If the variable is a reference.
     *                    Only ever true for reference parameters, false for non-reference parameters and local variable.
     */
    public VariableEntry(Type type, boolean isReference) {
        this.type = type;
        this.isReference = isReference;
    }

    @Override
    public String toString() {
        return String.format("var: %s%s", isReference ? "ref " : "", type);
    }
}
