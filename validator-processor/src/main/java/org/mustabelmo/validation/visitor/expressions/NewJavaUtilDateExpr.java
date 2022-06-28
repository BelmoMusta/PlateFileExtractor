package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.expr.Expression;

import java.time.LocalDate;


public class NewJavaUtilDateExpr extends CustomMethodCallExpr {
    public NewJavaUtilDateExpr(Expression caller, String regex) {
        super(null, null, null);
    }
    
    @Override
    protected Expression getInnerExpression() {
        int year=0;
        int month=0;
        int day=0;
        LocalDate localDate = LocalDate.of(year, month, day);
        
        return super.getInnerExpression();
    }
}
