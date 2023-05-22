package de.thm.mni.compilerbau.absyn;

/**
 * This class represents the position in the source code of any {@link Node}.
 */
public class Position {
    public final int line, column;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    /**
     * This variable is used as a placeholder when no position is present.
     * For example when throwing an error for a missing main procedure, which is required for a SPL program to have,
     * there is no position for this missing procedure. In such cases, this value is used.
     */
    public static final Position ERROR_POSITION = new Position(-1, -1);
}
