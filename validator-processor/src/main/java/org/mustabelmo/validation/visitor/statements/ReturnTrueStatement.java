package org.mustabelmo.validation.visitor.statements;


import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;

public class ReturnTrueStatement extends AbstractStatement {

    @Override
    public Statement getInnerStatement() {
        return new ReturnStmt("true");
    }
}
