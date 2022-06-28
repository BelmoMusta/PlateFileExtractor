package org.mustabelmo.validation.visitor.expressions;


import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OrExpression extends AbstractBinaryExpression {

    public OrExpression(Expression left, Expression right) {
        super(left, right);
    }

    public OrExpression(Expression left) {
        super(left);
    }

    public OrExpression(Object left) {
        super(new NameExpr(String.valueOf(left)));
    }

    public static Expression of(Collection array) {
        if (array == null || array.isEmpty()) {
            return new OrExpression(new BooleanLiteralExpr());
        }
        List<?> expressions = new ArrayList<>(array);
        if (expressions.size() == 1) {
            return new OrExpression(expressions.get(0));
        }

        Object firstExpression = expressions.get(0);
        Object secondExpression = expressions.get(1);
        OrExpression orExpression = new OrExpression(firstExpression, secondExpression);
        for (int i = 2; i < expressions.size(); i++) {
            Object expression = expressions.get(i);
            orExpression = new OrExpression(orExpression, expression);
        }

        return orExpression;
    }

    public static Expression of(Object... expressions) {
        return of(Arrays.asList(expressions));

    }

    public OrExpression(Expression left, Object right) {
        this(left, new NameExpr(right.toString()));
    }

    public OrExpression(Object left, Object right) {
        this(new NameExpr(left.toString()), new NameExpr(right.toString()));
    }

    @Override
    protected BinaryExpr.Operator getOperator() {
        return BinaryExpr.Operator.OR;
    }

}


