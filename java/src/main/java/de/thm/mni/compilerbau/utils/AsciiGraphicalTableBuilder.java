package de.thm.mni.compilerbau.utils;

import java.util.ArrayList;
import java.util.List;

public class AsciiGraphicalTableBuilder {
    static final char VERTICAL_SEP = '|';
    static final char HORIZONTAL_SEP = '─';
    static final char LEFT_CROSS = '├';
    static final char RIGHT_CROSS = '┤';
    static final char BOTTOM_LEFT_CORNER = '└';
    static final char BOTTOM_RIGHT_CORNER = '┘';

    public enum Alignment {
        LEFT,
        CENTER;

        public String pad(String s, int width, char paddingChar) {
            final var padding = String.valueOf(paddingChar);

            switch (this) {
                case LEFT:
                    return s + padding.repeat(width - s.length());
                case CENTER:
                    int padding_count = width - s.length();

                    int pre = padding_count / 2;
                    int post = padding_count - pre;

                    return (String.valueOf(paddingChar)).repeat(pre) + s + (String.valueOf(paddingChar)).repeat(post);
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    static class Line {
        char left, right;
        char padding;
        String content;
        Alignment contentAlignment;
        String comment;

        public Line(char left, char right, char padding, String content, Alignment contentAlignment, String comment) {
            this.left = left;
            this.right = right;
            this.padding = padding;
            this.content = content;
            this.contentAlignment = contentAlignment;
            this.comment = comment;
        }

        public int minWidth() {
            return content.length();
        }

        public void format(StringBuilder builder, int minWidth) {
            builder.append(this.left);
            builder.append(this.padding);
            builder.append(this.contentAlignment.pad(this.content, minWidth, this.padding));
            builder.append(this.padding);
            builder.append(this.right);
            builder.append(" ");
            builder.append(this.comment);
            builder.append('\n');
        }
    }

    List<Line> lines = new ArrayList<>();

    public String toString() {
        int minWidth = lines.stream().mapToInt(Line::minWidth).max().orElse(0);

        StringBuilder builder = new StringBuilder();
        lines.forEach(l -> l.format(builder, minWidth));
        return builder.toString();
    }

    public void line(String content, String comment, Alignment alignment) {
        this.lines.add(new Line(VERTICAL_SEP, VERTICAL_SEP, ' ', content, alignment, comment));
    }

    public void line(String content, Alignment alignment) {
        line(content, "", alignment);
    }

    public void sep(String content, String comment) {
        this.lines.add(new Line(LEFT_CROSS, RIGHT_CROSS, HORIZONTAL_SEP, content, Alignment.CENTER, comment));
    }

    public void sep(String content) {
        sep(content, "");
    }

    public void close(String content, String comment) {
        this.lines.add(new Line(BOTTOM_LEFT_CORNER, BOTTOM_RIGHT_CORNER, HORIZONTAL_SEP, content, Alignment.CENTER, comment));
    }

    public void close(String content) {
        close(content, "");
    }
}
