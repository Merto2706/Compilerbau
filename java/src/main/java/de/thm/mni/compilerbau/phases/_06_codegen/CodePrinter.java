package de.thm.mni.compilerbau.phases._06_codegen;

import java.io.PrintWriter;

class CodePrinter {
    private final PrintWriter outputFile;

    CodePrinter(PrintWriter outputFile) {
        this.outputFile = outputFile;
    }

    void emitInstruction(String opcode, Register r1, Register r2, Register r3) {
        outputFile.printf("\t%s\t%s,%s,%s\n", opcode, r1, r2, r3);
    }

    void emitInstruction(String opcode, Register r1, Register r2, int value) {
        outputFile.printf("\t%s\t%s,%s,%d\n", opcode, r1, r2, value);
    }

    void emitInstruction(String opcode, Register r1, Register r2, String label) {
        outputFile.printf("\t%s\t%s,%s,%s\n", opcode, r1, r2, label);
    }

    void emitInstruction(String opcode, Register r1) {
        outputFile.printf("\t%s\t%s\n", opcode, r1);
    }

    void emitInstruction(String opcode, String label) {
        outputFile.printf("\t%s\t%s\n", opcode, label);
    }

    void emitInstruction(String opcode, Register r1, Register r2, Register r3, String comment) {
        outputFile.printf("\t%s\t%s,%s,%s\t\t; %s\n", opcode, r1, r2, r3, comment);
    }

    void emitInstruction(String opcode, Register r1, Register r2, int value, String comment) {
        outputFile.printf("\t%s\t%s,%s,%d\t\t; %s\n", opcode, r1, r2, value, comment);
    }

    void emitInstruction(String opcode, Register r1, Register r2, String label, String comment) {
        outputFile.printf("\t%s\t%s,%s,%s\t\t; %s\n", opcode, r1, r2, label, comment);
    }

    void emitInstruction(String opcode, Register r1, String comment) {
        outputFile.printf("\t%s\t%s\t\t\t; %s\n", opcode, r1, comment);
    }

    void emitInstruction(String opcode, String label, String comment) {
        outputFile.printf("\t%s\t%s\t\t; %s\n", opcode, label, comment);
    }

    void emitLabel(String label) {
        outputFile.printf("%s:\n", label);
    }

    void emitImport(String label) {
        outputFile.printf("\t.import\t%s\n", label);
    }

    void emitExport(String label) {
        outputFile.printf("\t.export\t%s\n", label);
    }

    void emit(String str) {
        outputFile.println(str);
    }
}

