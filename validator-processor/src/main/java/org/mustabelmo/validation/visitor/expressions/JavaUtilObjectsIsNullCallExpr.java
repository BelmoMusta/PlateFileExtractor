package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.expr.Expression;


public class JavaUtilObjectsIsNullCallExpr extends CustomMethodCallExpr {

    public JavaUtilObjectsIsNullCallExpr(Expression... expression) {
        super("java.util.Objects.isNull", expression);
    }
}
