package de.thm.mni.compilerbau.phases._04b_semant;

import de.thm.mni.compilerbau.CommandLineOptions;
import de.thm.mni.compilerbau.absyn.*;
import de.thm.mni.compilerbau.absyn.visitor.DoNothingVisitor;
import de.thm.mni.compilerbau.phases._04a_tablebuild.TableBuilder;
import de.thm.mni.compilerbau.table.*;
import de.thm.mni.compilerbau.types.ArrayType;
import de.thm.mni.compilerbau.types.PrimitiveType;
import de.thm.mni.compilerbau.types.Type;
import de.thm.mni.compilerbau.utils.NotImplemented;
import de.thm.mni.compilerbau.utils.SplError;

/**
 * This class is used to check if the currently compiled SPL program is semantically valid.
 * The body of each procedure has to be checked, consisting of {@link Statement}s, {@link Variable}s and {@link Expression}s.
 * Each node has to be checked for type issues or other semantic issues.
 * Calculated {@link Type}s can be stored in and read from the dataType field of the {@link Expression} and {@link Variable} classes.
 */
public class ProcedureBodyChecker {
    private final CommandLineOptions commandLineOptions;

    public ProcedureBodyChecker(CommandLineOptions commandLineOptions) {
        this.commandLineOptions = commandLineOptions;

    }

    public void checkProcedures(Program program, SymbolTable globalTable) {
        //TODO (assignment 4b): Check all procedure bodies for semantic errors

        class TableBuilderVisitor extends DoNothingVisitor {
            SymbolTable table;

            TableBuilderVisitor(SymbolTable table) {
                this.table = table;
            }

            public void visit(Program program) {
                program.declarations.stream().filter(d -> d instanceof ProcedureDeclaration).forEach(p -> p.accept(this));
            }

            public void visit(AssignStatement assignStatement) {
                assignStatement.target.accept(this);
                assignStatement.value.accept(this);


                if (assignStatement.value.dataType != assignStatement.target.dataType)
                    throw SplError.IllegalAssignment(assignStatement.position, assignStatement.target.dataType, assignStatement.value.dataType);

                if (assignStatement.target.dataType != PrimitiveType.intType)
                    throw SplError.IllegalAssignmentToArray(assignStatement.position);
            }


            public void visit(CallStatement callStatement) {
                callStatement.arguments.forEach(f -> f.accept(this));

                if (!(table.lookup(callStatement.procedureName, SplError.UndefinedProcedure(callStatement.position,
                        callStatement.procedureName)) instanceof ProcedureEntry))
                    throw SplError.CallOfNonProcedure(callStatement.position, callStatement.procedureName);

                ProcedureEntry entry = (ProcedureEntry) table.lookup(callStatement.procedureName);


                if (callStatement.arguments.size() > entry.parameterTypes.size())
                    throw SplError.TooManyArguments(callStatement.position, callStatement.procedureName);


                if (callStatement.arguments.size() < entry.parameterTypes.size()) {
                    throw SplError.TooFewArguments(callStatement.position, callStatement.procedureName);
                }

                for (int i = 0; i < callStatement.arguments.size(); i++) {

                    if (!(callStatement.arguments.get(i) instanceof VariableExpression) && (entry.parameterTypes.get(i).isReference))
                        throw SplError.ArgumentMustBeAVariable(callStatement.position, callStatement.procedureName, i);

                    if (!(entry.parameterTypes.get(i).type == callStatement.arguments.get(i).dataType))
                        throw SplError.ArgumentTypeMismatch(callStatement.position, callStatement.procedureName, i,
                                entry.parameterTypes.get(i).type, callStatement.arguments.get(i).dataType);
                }
            }


            public void visit(IfStatement ifStatement) {
                ifStatement.condition.accept(this);
                ifStatement.elsePart.accept(this);
                ifStatement.thenPart.accept(this);

                if (!(ifStatement.condition.dataType == (PrimitiveType.boolType)))
                    throw SplError.IfConditionMustBeBoolean(ifStatement.position, ifStatement.condition.dataType);

            }

            public void visit(WhileStatement whileStatement) {
                whileStatement.condition.accept(this);
                whileStatement.body.accept(this);
                if (!(whileStatement.condition.dataType == (PrimitiveType.boolType)))
                    throw SplError.WhileConditionMustBeBoolean(whileStatement.position,
                            whileStatement.condition.dataType);

            }

            public void visit(CompoundStatement compoundStatement) {
                compoundStatement.statements.forEach(f -> f.accept(this));
            }


            public void visit(BinaryExpression binaryExpression) {
                binaryExpression.rightOperand.accept(this);
                binaryExpression.leftOperand.accept(this);

                //rechts und linke Operatoren ungleicher datentyp
                if (binaryExpression.leftOperand.dataType != binaryExpression.rightOperand.dataType)
                    throw SplError.NoSuchOperator(binaryExpression.position, binaryExpression.operator, binaryExpression.leftOperand.dataType, binaryExpression.rightOperand.dataType);
                // Früher throw SplError.OperatorDifferentTypes(binaryExpression.position, binaryExpression.leftOperand.dataType, binaryExpression.rightOperand.dataType);

                //wenn es kein Arithmetic op ist und einer der Operatoren kein int ist (doppelte prüfung maybe unnötig)
                if (!(binaryExpression.operator.isArithmetic())) {
                    if ((binaryExpression.leftOperand.dataType != PrimitiveType.intType) ||
                            (binaryExpression.rightOperand.dataType != PrimitiveType.intType))
                        throw SplError.NoSuchOperator(binaryExpression.position, binaryExpression.operator, binaryExpression.leftOperand.dataType, binaryExpression.rightOperand.dataType);
                    //Früher throw SplError.ComparisonNonInteger(binaryExpression.position, binaryExpression.dataType);

                    binaryExpression.dataType = PrimitiveType.boolType;
                } else { //wenn wir eine rechnung haben wo einer der beiden Operatoren kein int ist
                    if ((binaryExpression.leftOperand.dataType == PrimitiveType.boolType) &&
                            (binaryExpression.rightOperand.dataType == PrimitiveType.boolType))
                        throw SplError.NoSuchOperator(binaryExpression.position, binaryExpression.operator, binaryExpression.leftOperand.dataType, binaryExpression.rightOperand.dataType);
                    //throw SplError.ArithmeticOperatorNonInteger(binaryExpression.position, binaryExpression.dataType);

                    binaryExpression.dataType = PrimitiveType.intType;
                }
                binaryExpression.rightOperand.dataType = PrimitiveType.intType;
                binaryExpression.leftOperand.dataType = PrimitiveType.intType;
            }

            public void visit(UnaryExpression unaryExpression) {
                unaryExpression.operand.accept(this);

                // Prüfen, ob der Operand eine ganze Zahl ist
                if (unaryExpression.operand.dataType != PrimitiveType.intType) {
                    throw SplError.NoSuchOperator(unaryExpression.position, unaryExpression.operator, unaryExpression.operand.dataType);
                }
                unaryExpression.dataType = unaryExpression.operand.dataType;
            }

            public void visit(VariableExpression variableExpression) {
                variableExpression.variable.accept(this);
                variableExpression.dataType = variableExpression.variable.dataType;
            }

            public void visit(ArrayAccess arrayAccess) {
                arrayAccess.array.accept(this);
                arrayAccess.index.accept(this);

                if (!(arrayAccess.array.dataType instanceof ArrayType))
                    throw SplError.IndexingNonArray(arrayAccess.position);

                if (arrayAccess.index.dataType != PrimitiveType.intType)
                    throw SplError.IndexingWithNonInteger(arrayAccess.position);

                arrayAccess.dataType = ((ArrayType) arrayAccess.array.dataType).baseType;
                // arrayAccess.array.dataType
            }

            public void visit(ProcedureDeclaration procedureDeclaration) {
                ProcedureEntry entry = (ProcedureEntry) table.lookup(procedureDeclaration.name,
                        SplError.UndefinedProcedure(procedureDeclaration.position, procedureDeclaration.name));

                TableBuilderVisitor proceloc = new TableBuilderVisitor(entry.localTable);
                procedureDeclaration.body.forEach(p -> p.accept(proceloc));
            }

            public void visit(NamedVariable namedVariable) {
                if (!(table.lookup(namedVariable.name, SplError.UndefinedVariable(namedVariable.position,
                        namedVariable.name)) instanceof VariableEntry)) {
                    throw SplError.NotAVariable(namedVariable.position, namedVariable.name);
                }
                VariableEntry entry = (VariableEntry) table.lookup(namedVariable.name);
                namedVariable.dataType = entry.type;
            }

            public void visit(IntLiteral intLiteral) {
                intLiteral.dataType = PrimitiveType.intType;
            }

        }
        TableBuilderVisitor tb = new TableBuilderVisitor(globalTable);
        tb.visit(program);

    }
}