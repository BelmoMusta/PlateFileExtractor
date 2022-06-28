package org.mustabelmo.validation.visitor.statements;

import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

public abstract class AbstractStatement extends Statement {
    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return getInnerStatement().accept(v, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        getInnerStatement().accept(v, arg);
    }

    public abstract Statement getInnerStatement();
}
