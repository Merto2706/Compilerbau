package de.thm.mni.compilerbau.utils;

import de.thm.mni.compilerbau.absyn.BinaryExpression;
import de.thm.mni.compilerbau.absyn.Position;
import de.thm.mni.compilerbau.absyn.UnaryExpression;
import de.thm.mni.compilerbau.table.Identifier;
import de.thm.mni.compilerbau.types.Type;

/**
 * An exception class, that encapsulates all possible SPL errors.
 * Contains static methods that construct exceptions for specific errors.
 */
public class SplError extends RuntimeException {
    public final Position position;
    public final int errorCode;

    private SplError(int errorCode, Position position, String message, Object... formatArgs) {
        super(String.format(message, formatArgs));
        this.errorCode = errorCode;
        this.position = position;
    }

    public static SplError SyntaxError(Position position, String token) {
        return new SplError(100, position, "syntax error. Unexpected token '" + token + "'");
    }

    public static SplError UndefinedType(Position position, Identifier name) {
        return new SplError(101, position, "undefined type %s", name);
    }

    public static SplError NotAType(Position position, Identifier name) {
        return new SplError(102, position, "%s is not a type", name);
    }

    public static SplError RedeclarationAsType(Position position, Identifier name) {
        return new SplError(103, position, "redeclaration of %s as type", name);
    }

    public static SplError MustBeAReferenceParameter(Position position, Identifier name) {
        return new SplError(104, position, "parameter %s must be a reference parameter", name);
    }

    public static SplError RedeclarationAsProcedure(Position position, Identifier name) {
        return new SplError(105, position, "redeclaration of %s as procedure", name);
    }

    public static SplError RedeclarationAsParameter(Position position, Identifier name) {
        return new SplError(106, position, "redeclaration of %s as parameter", name);
    }

    public static SplError RedeclarationAsVariable(Position position, Identifier name) {
        return new SplError(107, position, "redeclaration of %s as variable", name);
    }

    public static SplError IllegalAssignment(Position position, Type left, Type right) {
        return new SplError(108, position, "illegal assignment '<%s> := <%s>'", left, right);
    }

    public static SplError IllegalAssignmentToArray(Position position) {
        return new SplError(109, position, "illegal assignment to array.");
    }

    public static SplError IfConditionMustBeBoolean(Position position, Type actual) {
        return new SplError(110, position, "'if' test expression must be of type boolean: actual = %s", actual);
    }

    public static SplError WhileConditionMustBeBoolean(Position position, Type actual) {
        return new SplError(111, position, "'while' test expression must be of type boolean: actual = %s", actual);
    }

    public static SplError UndefinedProcedure(Position position, Identifier name) {
        return new SplError(112, position, "undefined procedure %s", name);
    }

    public static SplError CallOfNonProcedure(Position position, Identifier name) {
        return new SplError(113, position, "call of non-procedure %s", name);
    }

    public static SplError ArgumentTypeMismatch(Position position, Identifier name, int argumentIndex, Type expected, Type actual) {
        return new SplError(114, position, "procedure %s argument %d type mismatch: expected = %s, actual = %s", name, argumentIndex, expected, actual);
    }

    public static SplError ArgumentMustBeAVariable(Position position, Identifier name, int argumentIndex) {
        return new SplError(115, position, "procedure %s argument %d must be a variable", name, argumentIndex);
    }

    public static SplError TooFewArguments(Position position, Identifier name) {
        return new SplError(116, position, "procedure %s called with too few arguments", name);
    }

    public static SplError TooManyArguments(Position position, Identifier name) {
        return new SplError(117, position, "procedure %s called with too many arguments", name);
    }

    public static SplError NoSuchOperator(Position position, BinaryExpression.Operator operator, Type leftType, Type rightType){
        return new SplError(118, position, "There is no binary operator '<%s> %s <%s>'.", leftType, operator.operatorString(), rightType);
    }

    public static SplError NoSuchOperator(Position position, UnaryExpression.Operator operator, Type rightType){
        return new SplError(119, position, "There is no unary operator '%s <%s>'.", operator.operatorString(), rightType);
    }

    public static SplError UndefinedVariable(Position position, Identifier name) {
        return new SplError(121, position, "undefined variable %s", name);
    }

    public static SplError NotAVariable(Position position, Identifier name) {
        return new SplError(122, position, "%s is not a variable", name);
    }

    public static SplError IndexingNonArray(Position position) {
        return new SplError(123, position, "illegal indexing a non-array");
    }

    public static SplError IndexingWithNonInteger(Position position) {
        return new SplError(124, position, "illegal indexing with a non-integer");
    }

    public static SplError MainIsMissing() {
        return new SplError(125, Position.ERROR_POSITION, "procedure 'main' is missing");
    }

    public static SplError MainIsNotAProcedure() {
        return new SplError(126, Position.ERROR_POSITION, "'main' is not a procedure");
    }

    public static SplError MainMustNotHaveParameters() {
        return new SplError(127, Position.ERROR_POSITION, "procedure 'main' must not have any parameters");
    }

    public static SplError IllegalApostrophe(Position position) {
        return new SplError(99, position, "illegal use of apostrophe");
    }

    public static SplError IllegalCharacter(Position position, char character) {
        return new SplError(99, position,
                "Illegal character %s",
                Character.isISOControl(character) ? "0x" + Integer.toString(character, 16) : "'" + character + "'");
    }

    public static SplError RegisterOverflow() {
        return new SplError(140, Position.ERROR_POSITION, "There are not enough registers to run this program!");
    }
}
