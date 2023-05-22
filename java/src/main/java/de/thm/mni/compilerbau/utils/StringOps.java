package de.thm.mni.compilerbau.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringOps {
    public static String indent(String str, int indentation) {
        // TODO: We are now on Java 17, String.ident() does exist and we can remove this.
        final char[] indentationChars = new char[indentation];
        Arrays.fill(indentationChars, ' ');

        final String indentationPrefix = String.valueOf(indentationChars);

        return str.lines().map(s -> indentationPrefix + s).collect(Collectors.joining("\n"));
    }

    public static String toString(Object o) {
        return o == null ? "NULL" : o.toString();
    }
}
