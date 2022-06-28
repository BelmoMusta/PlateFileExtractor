package org.mustabelmo.validation.processor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassWrapper {
    private final CompilationUnit compilationUnit;
    private final String generatedClassName;
    private Element annotatedElement;
    private final ClassOrInterfaceDeclaration destinationClass;
    private List<WrappedElement> enclosedElements;

    public ClassWrapper(CompilationUnit compilationUnit, String generatedClassName) {
        this.compilationUnit = compilationUnit;
        this.generatedClassName = generatedClassName;
        destinationClass = compilationUnit.addClass(generatedClassName);
    }

    public static ClassWrapper of(Element annotatedElement) {
        CompilationUnit compilationUnit = new CompilationUnit();
        String generatedClassName = annotatedElement.getSimpleName().toString() + "Extractor";
        ClassWrapper classWrapper = new ClassWrapper(compilationUnit, generatedClassName);
        String packageName = "org.mustabelmo.validation";
        classWrapper.getCompilationUnit().setPackageDeclaration(packageName);
        classWrapper.setAnnotatedElement(annotatedElement);

        return classWrapper;
    }
    

    public List<WrappedElement> getFieldList() {
        return enclosedElements.stream().
                filter(WrappedElement::isField)
                .collect(Collectors.toList());

    }


    public Map<String, WrappedElement> getCorrespondanceFieldMethod() {
        Map<String, WrappedElement> methodFieldMapper = new LinkedHashMap<>();
        for (WrappedElement enclosedElement : this.enclosedElements) {
            if (enclosedElement.isMethod() && enclosedElement.isNotVoidOrAbstract()) {
                methodFieldMapper.put(enclosedElement.getPossibleFieldNameForMethod(), enclosedElement);
            }
        }
        return methodFieldMapper;
    }

    public Element getAnnotatedElement() {
        return annotatedElement;
    }

    public void setAnnotatedElement(Element annotatedElement) {
        this.annotatedElement = annotatedElement;
        this.enclosedElements = annotatedElement.getEnclosedElements().stream()
                .map(WrappedElement::new)
                .collect(Collectors.toList());
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public String getGeneratedClassName() {
        return generatedClassName;
    }

    public ClassOrInterfaceDeclaration getDestinationClass() {
        return destinationClass;
    }

    public BlockStmt addValidationMethod() {

        final Name qualifiedName = ((TypeElement) annotatedElement).getQualifiedName();
        final MethodDeclaration method = destinationClass.addMethod("extract", Modifier.PUBLIC, Modifier.STATIC);
        final Parameter parameter = new Parameter();
        final BlockStmt body = new BlockStmt();
        method.setType(qualifiedName.toString());
        parameter.setType("String");
        parameter.setName("inputLine");
        method.addParameter(parameter);
        method.setBody(body);
        return body;
    }
    
    public String addNewObjectCreation(BlockStmt body) {
        final Name qualifiedName = ((TypeElement) annotatedElement).getQualifiedName();
        final Name shortName =  annotatedElement.getSimpleName();
        String frame = "%s a%s = new %s();";
        String statement = String.format(frame, qualifiedName, shortName, qualifiedName);
        body.addStatement(statement);
        return "a"+shortName.toString();
        
    }
}
