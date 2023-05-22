package de.thm.mni.compilerbau.table;

/**
 * Represents an identifier in SPL.
 * Implements string interning internally to speed up lookups in symbol tables.
 */
public class Identifier {
    private final String identifier;

    public Identifier(String identifier) {
        //Intern the identifier.
        //This way string equality in table lookups can be determined by only comparing the references.
        this.identifier = identifier.intern();
    }

    public int hashCode() {
        return identifier.hashCode();
    }

    public boolean equals(Object other) {
        return other instanceof Identifier && ((Identifier) other).identifier.equals(identifier);
    }

    @Override
    public String toString() {
        return identifier;
    }
}
