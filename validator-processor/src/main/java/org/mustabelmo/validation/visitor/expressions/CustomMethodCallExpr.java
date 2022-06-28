package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;


public abstract class CustomMethodCallExpr extends AbstractExpression {
    private final MethodCallExpr methodCallExpr;

    public CustomMethodCallExpr(String name, Expression... params) {
        methodCallExpr = new MethodCallExpr(name, params);
    }

    @Override
    protected Expression getInnerExpression() {
        return methodCallExpr;
    }
}
