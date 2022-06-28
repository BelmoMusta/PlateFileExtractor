package org.mustabelmo.validation.visitor.expressions;


import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;

public abstract class AbstractBinaryExpression extends AbstractExpression {
    private final Expression expression;

    protected AbstractBinaryExpression(Expression left, Expression right) {
        expression = new BinaryExpr(left, right, getOperator());
    }

    protected AbstractBinaryExpression(Expression left) {
        expression = left;
    }

    protected abstract BinaryExpr.Operator getOperator();

    @Override
    protected Expression getInnerExpression() {
        return expression;
    }
}


