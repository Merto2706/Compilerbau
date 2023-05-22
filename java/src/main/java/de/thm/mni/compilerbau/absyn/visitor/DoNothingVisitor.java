package de.thm.mni.compilerbau.absyn.visitor;

import de.thm.mni.compilerbau.absyn.*;

/**
 * This implementation of a {@link Visitor} implements every visit-method but does nothing in every case.
 * <p>
 * It can be used to hide unneeded implementations in a visitor.
 * If for example a visitor would only need a visit-method for the {@link Program}-node, the java code would not compile
 * until every other method is implemented. When the {@link DoNothingVisitor} is extended in this case, all other methods
 * are implemented in this superclass and thus no compile-errors are produced.
 */
public class DoNothingVisitor implements Visitor {
    @Override
    public void visit(ArrayAccess arrayAccess) {
    }

    @Override
    public void visit(ArrayTypeExpression arrayTypeExpression) {
    }

    @Override
    public void visit(AssignStatement assignStatement) {
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
    }

    @Override
    public void visit(CallStatement callStatement) {
    }

    @Override
    public void visit(CompoundStatement compoundStatement) {
    }

    @Override
    public void visit(EmptyStatement emptyStatement) {
    }

    @Override
    public void visit(IfStatement ifStatement) {
    }

    @Override
    public void visit(IntLiteral intLiteral) {
    }

    @Override
    public void visit(NamedTypeExpression namedTypeExpression) {
    }

    @Override
    public void visit(NamedVariable namedVariable) {
    }

    @Override
    public void visit(ParameterDeclaration parameterDeclaration) {
    }

    @Override
    public void visit(ProcedureDeclaration procedureDeclaration) {
    }

    @Override
    public void visit(Program program) {
    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {
    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {
    }

    @Override
    public void visit(VariableExpression variableExpression) {
    }

    @Override
    public void visit(WhileStatement whileStatement) {
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
    }
}
