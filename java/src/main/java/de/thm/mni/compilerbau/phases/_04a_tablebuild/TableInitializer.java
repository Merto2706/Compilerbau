package de.thm.mni.compilerbau.phases._04a_tablebuild;

import de.thm.mni.compilerbau.CommandLineOptions;
import de.thm.mni.compilerbau.phases._05_varalloc.VarAllocator;
import de.thm.mni.compilerbau.table.*;
import de.thm.mni.compilerbau.types.PrimitiveType;

import java.util.List;

final class TableInitializer {
    private TableInitializer() {
    }

    /**
     * Creates a new SymbolTable and enters entries for all predefined types and procedures.
     *
     * @param options The command line options supplied to the compiler
     * @return A new instance of the symbol table representing the global definition scope.
     */
    static SymbolTable initializeGlobalTable(CommandLineOptions options) {
        SymbolTable table = new SymbolTable();
        enterPredefinedTypes(table, options);
        enterPredefinedProcedures(table, options);
        return table;
    }


    private static void enterPredefinedTypes(SymbolTable table, CommandLineOptions options) {
        table.enter(new Identifier("int"), new TypeEntry(PrimitiveType.intType));
    }

    private static void enterPredefinedProcedures(SymbolTable table, CommandLineOptions options) {
        // printi(i: int)
        table.enter(new Identifier("printi"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, false, 0)),
                PrimitiveType.intType.byteSize));

        // printc(i: int)
        table.enter(new Identifier("printc"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, false, 0)),
                PrimitiveType.intType.byteSize));
        // readi(ref i: int)
        table.enter(new Identifier("readi"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, true, 0)),
                VarAllocator.REFERENCE_BYTESIZE));
        // readc(ref i: int)
        table.enter(new Identifier("readc"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, true, 0)),
                VarAllocator.REFERENCE_BYTESIZE));
        // exit()
        table.enter(new Identifier("exit"), ProcedureEntry.predefinedProcedureEntry(List.of(), 0));
        // time(ref i: int)
        table.enter(new Identifier("time"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, true, 0)),
                VarAllocator.REFERENCE_BYTESIZE));
        // clearAll(color: int)
        table.enter(new Identifier("clearAll"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, false, 0)),
                PrimitiveType.intType.byteSize));
        // setPixel(x: int, y: int, color: int)
        table.enter(new Identifier("setPixel"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, false, 0),
                new ParameterType(PrimitiveType.intType, false, PrimitiveType.intType.byteSize),
                new ParameterType(PrimitiveType.intType, false, 2 * PrimitiveType.intType.byteSize)),
                3 * PrimitiveType.intType.byteSize));
        // drawLine(x1: int, y1: int, x2: int, y2: int, color: int)
        table.enter(new Identifier("drawLine"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, false, 0),
                new ParameterType(PrimitiveType.intType, false, PrimitiveType.intType.byteSize),
                new ParameterType(PrimitiveType.intType, false, 2 * PrimitiveType.intType.byteSize),
                new ParameterType(PrimitiveType.intType, false, 3 * PrimitiveType.intType.byteSize),
                new ParameterType(PrimitiveType.intType, false, 4 * PrimitiveType.intType.byteSize)),
                5 * PrimitiveType.intType.byteSize));
        // drawCircle(x0: int, y0: int, radius: int, color: int)
        table.enter(new Identifier("drawCircle"), ProcedureEntry.predefinedProcedureEntry(List.of(
                new ParameterType(PrimitiveType.intType, false, 0),
                new ParameterType(PrimitiveType.intType, false, PrimitiveType.intType.byteSize),
                new ParameterType(PrimitiveType.intType, false, 2 * PrimitiveType.intType.byteSize),
                new ParameterType(PrimitiveType.intType, false, 3 * PrimitiveType.intType.byteSize)),
                4 * PrimitiveType.intType.byteSize));
    }

}
