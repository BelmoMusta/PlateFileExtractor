package org.mustabelmo.validation.visitor.statements;


import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.mustabelmo.validation.visitor.expressions.MatchRegexMethodCallExpr;


public class RegexCheckIfStatement extends AbstractStatement {
    private final IfStmt ifStmt;


    public RegexCheckIfStatement(Expression caller, String regex) {
        final Expression condition = new MatchRegexMethodCallExpr(caller, regex);
        final Expression unaryExpr = new UnaryExpr(condition, UnaryExpr.Operator.LOGICAL_COMPLEMENT);
        ifStmt = new IfStmt()
                .setCondition(unaryExpr)
                .setThenStmt(new BlockStmt().addStatement(new ReturnFalseBlock()));
    }

    @Override
    public Statement getInnerStatement() {
        return ifStmt;
    }
}
