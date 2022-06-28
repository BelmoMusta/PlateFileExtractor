package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.expr.Expression;

public class ArrayLengthMethodCallExpr extends CustomMethodCallExpr {
    public ArrayLengthMethodCallExpr(Expression... params) {
        super("arrayLength", params);
    }
}
