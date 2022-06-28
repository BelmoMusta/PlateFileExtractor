package org.mustabelmo.validation.visitor.statements;


import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;

public class ReturnNullStatement extends AbstractStatement {

    @Override
    public Statement getInnerStatement() {
        return new ReturnStmt(new NullLiteralExpr());
    }
}
