package org.mustabelmo.validation.visitor.wrappers;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.utils.StringEscapeUtils;
import org.mustabelmo.validation.visitor.expressions.AbstractExpression;

public class EscapedParameter extends AbstractExpression {
    final String parameter;

    public EscapedParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    protected Expression getInnerExpression() {
        String escaped = StringEscapeUtils.escapeJava(parameter);
        return new StringLiteralExpr(escaped);
    }
}
