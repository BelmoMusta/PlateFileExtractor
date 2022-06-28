package org.mustabelmo.validation.visitor.statements;


import com.github.javaparser.ast.expr.Expression;
import org.mustabelmo.validation.visitor.expressions.JavaUtilObjectsIsNullCallExpr;

public class NotNullCheckIfStatement extends AbstractIfStatement {

    public NotNullCheckIfStatement(Expression expression) {
        super(expression);
    }

    @Override
    protected Expression getCondition(Expression expression) {
        return new JavaUtilObjectsIsNullCallExpr(expression);
    }

}
