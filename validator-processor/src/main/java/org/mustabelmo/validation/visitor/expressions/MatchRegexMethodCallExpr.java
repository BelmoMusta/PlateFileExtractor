package org.mustabelmo.validation.visitor.expressions;

import com.github.javaparser.ast.expr.Expression;
import org.mustabelmo.validation.visitor.wrappers.EscapedParameter;


public class MatchRegexMethodCallExpr extends CustomMethodCallExpr {
    public MatchRegexMethodCallExpr(Expression caller, String regex) {
        super("java.util.regex.Pattern.matches", new EscapedParameter(regex), caller);
    }
}
