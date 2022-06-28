package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.expr.Expression;

public class LengthMethodCallExpr extends CustomMethodCallExpr {
    public LengthMethodCallExpr(Expression... params) {
        super("length", params);
    }
}
