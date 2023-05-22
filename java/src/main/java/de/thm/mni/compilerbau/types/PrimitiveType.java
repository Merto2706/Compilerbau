package de.thm.mni.compilerbau.types;

/**
 * Represents built in types (int, bool) of spl.
 * All available built in types are available as static methods of this class.
 */
public class PrimitiveType extends Type {
    private final String printName;

    /**
     * Creates a new instance for builtin types in SPL.
     *
     * @param byteSize  The size of the type in bytes.
     * @param printName The name of the type. Used for printing it, does not mean the type can be referenced in spl source code with this name.
     */
    private PrimitiveType(int byteSize, String printName) {
        super(byteSize);
        this.printName = printName;
    }

    @Override
    public String toString() {
        return printName;
    }

    public static final PrimitiveType intType = new PrimitiveType(4, "int");
    public static final PrimitiveType boolType = new PrimitiveType(4, "boolean");
}
