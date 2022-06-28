package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;


public class ObjMethodCallExpr extends AbstractExpression {
    private final MethodCallExpr methodCallExpr;

    public ObjMethodCallExpr(Parameter caller, MethodDeclaration  methodDeclaration) {
        methodCallExpr = new MethodCallExpr();
        methodCallExpr.setScope(caller.getNameAsExpression());
        methodCallExpr.setName(methodDeclaration.getName());
    }

    public ObjMethodCallExpr(String caller, String  methodName) {
        methodCallExpr = new MethodCallExpr();
        methodCallExpr.setScope(new NameExpr(caller));
        methodCallExpr.setName(methodName);
    }

    @Override
    protected Expression getInnerExpression() {
        return methodCallExpr;
    }
}
