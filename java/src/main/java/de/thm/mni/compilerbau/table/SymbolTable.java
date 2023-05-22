package de.thm.mni.compilerbau.table;

import de.thm.mni.compilerbau.utils.SplError;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a symbol table for a definition scope in SPL.
 * Maps identifiers to the corresponding symbols.
 */
public class SymbolTable {
    private final Map<Identifier, Entry> entries = new HashMap<>();
    private final SymbolTable upperLevel;

    /**
     * Constructs a local table representing a local definition scope.
     *
     * @param upperLevel The symbol table for the surrounding scope.
     */
    public SymbolTable(SymbolTable upperLevel) {
        this.upperLevel = upperLevel;
    }

    /**
     * Constructs an empty table with no surrounding scope.
     */
    public SymbolTable() {
        this.upperLevel = null;
    }

    /**
     * Returns the {@link SymbolTable} of the upper scope if present.
     *
     * @return The upper {@link SymbolTable} or empty.
     */
    public Optional<SymbolTable> getUpperLevel() {
        return Optional.ofNullable(upperLevel);
    }

    /**
     * Inserts a new symbol into the table.
     * Does nothing if a symbol with this name already exists in this scope.
     *
     * @param name  The name of the symbol that is entered.
     * @param entry The entry for the new symbol.
     */
    public void enter(Identifier name, Entry entry) {
        this.entries.putIfAbsent(name, entry);
    }

    /**
     * Inserts a new symbol into the table.
     * Throws an exception if a symbol with this name already exists in this scope.
     * 
     * @param name  The name of the symbol that is entered.
     * @param entry The entry for the new symbol.
     * @param error The exception to throw if a symbol with this name is already defined.
     * @throws SplError If a symbol with this name is already defined.
     */
    public void enter(Identifier name, Entry entry, SplError error) {
        if (this.entries.containsKey(name))
            throw error;

        this.enter(name, entry);
    }

    /**
     * Looks for the symbol defined with the given name.
     * Recursively looks in outer scopes if the name is not defined in this scope.
     *
     * @param name The name of the symbol.
     * @return null if no symbol was found, the found symbol otherwise.
     */
    public Entry lookup(Identifier name) {
        Entry entry = this.entries.get(name);

        if (entry != null) return entry;
        if (upperLevel != null) return upperLevel.lookup(name);
        return null;
    }

    /**
     * Looks for the symbol defined with the given name.
     * Recursively looks in outer scopes if the name is not defined in this scope.
     *
     * @param name The name of the symbol.
     * @return The symbol belonging to this name.
     * @throws SplError If there is no symbol with this name.
     * @see SymbolTable#find(Identifier)
     */
    public Entry lookup(Identifier name, SplError error) {
        return find(name).orElseThrow(() -> error);
    }

    /**
     * Tries to find the symbol defined with the given name.
     * If there is no symbol with that name in the current or any outer scope, {@link Optional#empty()} is returned.
     *
     * @param name The name of the symbol.
     * @return The symbol belonging to this name or empty.
     * @see SymbolTable#lookup(Identifier, SplError)
     */
    public Optional<Entry> find(Identifier name) {
        return Optional.ofNullable(lookup(name));
    }

    /**
     * Converts the table to a human-readable format.
     *
     * @param level       The level of this scope. 0 for the most inner scope, +1 for each outer scope.
     * @return A human readable representation of the table contents.
     */
    public String toString(int level) {
        var string = String.format("  level %d\n", level);

        if (this.entries.size() == 0) string += "    <empty>\n";
        else {
            string += this.entries.entrySet().stream()
                    .sorted(Comparator.comparing(a -> a.getKey().toString()))
                    .map(entry -> String.format("    %-15s --> %s\n", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining(""));
        }

        if (upperLevel != null) string += upperLevel.toString(level + 1);

        return string;
    }

    /**
     * @return A human readable representation of the table contents.
     */
    @Override
    public String toString() {
        return this.toString(0);
    }
}
