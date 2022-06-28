package org.mustabelmo.validation.visitor.expressions;


import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class AndExpression extends AbstractBinaryExpression {

    public AndExpression(Expression left, Expression right) {
        super(left, right);
    }

    public AndExpression(Object left, Expression right) {
        this(new NameExpr(left.toString()), right);
    }

    public AndExpression(Expression left, Object right) {
        this(left, new NameExpr(right.toString()));
    }

    public AndExpression(Object left, Object right) {
        this(new NameExpr(left.toString()), new NameExpr(right.toString()));
    }

    @Override
    protected BinaryExpr.Operator getOperator() {
        return BinaryExpr.Operator.AND;
    }


    public static Expression of(Collection array) {
        if (array == null || array.isEmpty()) {
            return new AndExpression(new BooleanLiteralExpr(true), new BooleanLiteralExpr(true));
        }
        ArrayList expressions = new ArrayList(array);
        if (expressions.size() == 1) {
            return new AndExpression(expressions.get(0), new BooleanLiteralExpr(false));
        }

        Object firstExpression = expressions.get(0);
        Object secondExpression = expressions.get(1);
        AndExpression orExpression = new AndExpression(firstExpression, secondExpression);
        for (int i = 2; i < expressions.size(); i++) {
            Object expression = expressions.get(i);
            orExpression = new AndExpression(orExpression, expression);
        }

        return orExpression;
    }

    public static Expression of(Object... expressions) {
        return of(Arrays.asList(expressions));
    }

}
