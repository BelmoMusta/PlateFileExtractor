package org.mustabelmo.validation.visitor.expressions;


import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

public class LessThanExpression extends AbstractBinaryExpression {

    public LessThanExpression(Expression left, Expression right) {
        super(left, right);
    }

    public LessThanExpression(Object left, Expression right) {
        this(new NameExpr(left.toString()), right);
    }

    public LessThanExpression(Expression left, Object right) {
        this(left, new NameExpr(right.toString()));
    }

    public LessThanExpression(Object left, Object right) {
        this(new NameExpr(left.toString()), new NameExpr(right.toString()));
    }

    public LessThanExpression(Expression left, String right) {
        this(left, new StringLiteralExpr(right));
    }

    public LessThanExpression(String left, Expression right) {
        this(new StringLiteralExpr(left), right);
    }

    public LessThanExpression(String left, String right) {
        this(new StringLiteralExpr(left), new StringLiteralExpr(right));
    }

    @Override
    protected BinaryExpr.Operator getOperator() {
        return BinaryExpr.Operator.LESS;
    }

}


