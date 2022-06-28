package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

public abstract class AbstractExpression extends Expression {

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return getInnerExpression().accept(v, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        getInnerExpression().accept(v, arg);
    }

    protected abstract Expression getInnerExpression();
}
