package org.mustabelmo.validation.visitor.statements;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

import javax.lang.model.type.TypeKind;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class TypeExpressionFactory {
    static Map<TypeKind, Function<String, Expression>> map = new LinkedHashMap<>();

    static {
        map.put(TypeKind.BOOLEAN, NameExpr::new);
        map.put(TypeKind.INT, NameExpr::new);
        map.put(TypeKind.BYTE, NameExpr::new);
        map.put(TypeKind.SHORT, NameExpr::new);
        map.put(TypeKind.CHAR, TypeExpressionFactory::createExpression);
        map.put(TypeKind.DECLARED, StringLiteralExpr::new);
    }

    private static NameExpr createExpression(String name) {
        String quotes;
        if (name.length() == 1) {
            quotes = "'";
        } else {
            quotes = "\"";
        }
        return new NameExpr(quotes + name + quotes);
    }

    public static Expression getExpression(String literal, TypeKind typeKind) {
        return map.getOrDefault(typeKind, TypeExpressionFactory::createExpression)
                .apply(literal);
    }

}
