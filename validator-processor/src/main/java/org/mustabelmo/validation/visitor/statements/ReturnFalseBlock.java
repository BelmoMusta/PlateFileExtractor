package org.mustabelmo.validation.visitor.statements;

import com.github.javaparser.ast.stmt.Statement;

public class ReturnFalseBlock extends AbstractStatement {
    @Override
    public Statement getInnerStatement() {
        return new ReturnFalseStatement();
    }
}


