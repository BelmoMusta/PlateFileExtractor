package org.mustabelmo.validation.visitor.statements;


import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.mustabelmo.validation.visitor.expressions.EqualsMethodCallExpr;

import javax.lang.model.type.TypeKind;


public class MustEqualCheckIfStatement extends AbstractStatement {
    private final IfStmt ifStmt;


    public MustEqualCheckIfStatement(Expression left, Expression right) {
        final Expression condition = new EqualsMethodCallExpr(left, right);
        final Expression unaryExpr = new UnaryExpr(condition, UnaryExpr.Operator.LOGICAL_COMPLEMENT);
        ifStmt = new IfStmt()
                .setCondition(unaryExpr)
                .setThenStmt(new BlockStmt().addStatement(new ReturnFalseBlock()));
    }

    public MustEqualCheckIfStatement(Expression left, String right, TypeKind kind) {
        //todo
        this(left, TypeExpressionFactory.getExpression(right, kind));
    }

    @Override
    public Statement getInnerStatement() {
        return ifStmt;
    }
}
