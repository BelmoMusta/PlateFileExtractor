package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.expr.Expression;


public class EqualsMethodCallExpr extends CustomMethodCallExpr {
    public EqualsMethodCallExpr(Expression ... params) {
        super("java.util.Objects.equals", params);
    }
}
