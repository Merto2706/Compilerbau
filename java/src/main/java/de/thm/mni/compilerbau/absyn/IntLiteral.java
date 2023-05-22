package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

import java.util.Objects;

/**
 * This class represents a literal in SPL.
 * Examples: 12, 0x47 or 'a'
 * <p>
 * Every time a number is used in an SPL program (decimal, hexadecimal or a character), this number is represented as
 * an integer. This numbers in the source code are called literals.
 */
public final class IntLiteral extends Expression {
    public final int value;

    /**
     * Creates a new node representing an integer literal.
     *
     * @param position The position of the literal in the source code.
     * @param value    The value the literal holds.
     */
    public IntLiteral(Position position, Integer value) {
        super(position);
        this.value = Objects.requireNonNull(value, "Invalid value null for IntLiteral!");
    }

    @Override
    public String toString() {
        return formatAst("IntLiteral", value);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
