package org.mustabelmo.validation.visitor.expressions;


import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

public class GreaterThanExpression extends AbstractBinaryExpression {

    public GreaterThanExpression(Expression left, Expression right) {
        super(left, right);
    }

    public GreaterThanExpression(Object left, Expression right) {
        this(new NameExpr(left.toString()), right);
    }

    public GreaterThanExpression(Expression left, Object right) {
        this(left, new NameExpr(right.toString()));
    }

    public GreaterThanExpression(Object left, Object right) {
        this(new NameExpr(left.toString()), new NameExpr(right.toString()));
    }

    public GreaterThanExpression(Expression left, String right) {
        this(left, new StringLiteralExpr(right));
    }

    public GreaterThanExpression(String left, Expression right) {
        this(new StringLiteralExpr(left), right);
    }

    public GreaterThanExpression(String left, String right) {
        this(new StringLiteralExpr(left), new StringLiteralExpr(right));
    }

    @Override
    protected BinaryExpr.Operator getOperator() {
        return BinaryExpr.Operator.GREATER;
    }

}


