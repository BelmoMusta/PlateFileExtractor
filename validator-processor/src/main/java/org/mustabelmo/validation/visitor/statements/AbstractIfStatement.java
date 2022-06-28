package org.mustabelmo.validation.visitor.statements;


import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

public abstract class AbstractIfStatement extends AbstractStatement {
    final IfStmt ifStmt;

    protected AbstractIfStatement(Expression expression) {
        ifStmt = new IfStmt()
                .setCondition(getCondition(expression))
                .setThenStmt(getThenStmt());
    }
    
    protected BlockStmt getThenStmt() {
        return new BlockStmt().addStatement(new ReturnFalseBlock());
    }
    
    protected abstract Expression getCondition(Expression expression);

    @Override
    public Statement getInnerStatement() {
        return ifStmt;
    }
}
