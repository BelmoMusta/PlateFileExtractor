package org.mustabelmo.validation.processor;


import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import org.mustabelmo.validation.annotation.Field;
import org.mustabelmo.validation.visitor.statements.ReturnNullStatement;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Set;


@SupportedAnnotationTypes("org.mustabelmo.validation.annotation.Mappable")
public class ValidationProcessor extends AbstractProcessor {
	
	public ValidationProcessor() {
	
	}
	
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
		try {
			for (TypeElement annotation : annotations) {
				
				Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
				for (Element aClass : annotatedElements) {
					final ClassWrapper classWrapper = ClassWrapper.of(aClass);
					final BlockStmt validationMethod = classWrapper.addValidationMethod();
					String nameOfVariable = classWrapper.addNewObjectCreation(validationMethod);
					List<WrappedElement> fieldList = classWrapper.getFieldList();
					addStringBuilder(validationMethod);
					for (WrappedElement wrappedElement : fieldList) {
						validationMethod.addStatement(createStatement(validationMethod, nameOfVariable,
								wrappedElement));
					}
					validationMethod.addStatement(new ReturnStmt(new NameExpr(nameOfVariable)));
					JavaFileWriter.writeJavaClass(classWrapper, processingEnv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private Expression createStatement(BlockStmt validationMethod, String nameOfVariable,
									   WrappedElement wrappedElement) {
		
		Field annotation = wrappedElement.getAnnotation(Field.class);
		
		int begin = annotation.begin();
		int end = annotation.end();
		String name = "a" + wrappedElement.getName() + "Value";
		
		MethodCallExpr substringCall = new MethodCallExpr();
		substringCall.setName("substring");
		substringCall.setScope(new NameExpr("stringBuilder"));
		substringCall.addArgument(String.valueOf(begin));
		substringCall.addArgument(String.valueOf(end));
		
		VariableDeclarationExpr variable = new VariableDeclarationExpr();
		VariableDeclarator variableDeclarator = new VariableDeclarator();
		variableDeclarator.setType("java.lang.String");
		variableDeclarator.setName(name);
		variableDeclarator.setInitializer(substringCall);
		variable.addVariable(variableDeclarator);
		
		validationMethod.addStatement(variable);
		MethodCallExpr setterCall = new MethodCallExpr();
		setterCall.setScope(new NameExpr(nameOfVariable));
		setterCall.setName("set" + wrappedElement.getName());
		setterCall.addArgument(name);
		return setterCall;
	}
	
	private void addStringBuilder(BlockStmt validationMethod) {
		VariableDeclarationExpr variable = new VariableDeclarationExpr();
		VariableDeclarator variableDeclarator = new VariableDeclarator();
		variableDeclarator.setType("java.lang.StringBuilder");
		variableDeclarator.setName("stringBuilder");
		
		ObjectCreationExpr newStringBuilderCall = new ObjectCreationExpr();
		newStringBuilderCall.setType("java.lang.StringBuilder");
		newStringBuilderCall.addArgument("inputLine");
		variableDeclarator.setInitializer(newStringBuilderCall);
		variable.addVariable(variableDeclarator);
		validationMethod.addStatement(variable);
	}
	
	
}