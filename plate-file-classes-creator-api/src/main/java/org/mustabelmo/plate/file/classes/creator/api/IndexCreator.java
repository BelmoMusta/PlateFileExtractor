package org.mustabelmo.plate.file.classes.creator.api;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import org.mustabelmo.plate.file.classes.creator.api.annotation.Field;
import org.mustabelmo.plate.file.classes.creator.api.annotation.Mappable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static com.github.javaparser.utils.Utils.capitalize;

public class IndexCreator {
    public static void main0(String[] args) {
        System.out.println("it works");
    }

    public static Map<String, Structure> readFile(String file) throws Exception {
        File absoluteFile = new File(file);
        Scanner scanner = new Scanner(absoluteFile);

        Map<String, Structure> structures = new LinkedHashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(";");
            String partOne = lineScanner.next();
            String partTwo = lineScanner.next();
            String className = partOne + partTwo;
            Structure structure = structures.get(className);
            if (structure == null) {
                structure = new Structure();
                structures.put(className, structure);
                structure.setClassName(className);
            }
            int start = lineScanner.nextInt();
            String name = lineScanner.next();
            lineScanner.next();
            int length = lineScanner.nextInt();
            int end = start + length - 1;
            String comment = lineScanner.next();
            FieldStructure fieldStructure = new FieldStructure();
            fieldStructure.setName(name);
            fieldStructure.setStart(start);
            fieldStructure.setEnd(end);
            fieldStructure.setComment(comment);
            structure.add(fieldStructure);
        }
        return structures;

    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length <= 2) {
            return;
        }
        String file = args[0];
        String destDirectoryName = args[1];
        String destPackage = args[2];
        String subPackageDirectory = destPackage.replaceAll("\\.", "/");
        File destDirectory = new File(destDirectoryName, subPackageDirectory);
        destDirectory.mkdirs();


        Map<String, Structure> stringStructureMap = readFile(file);
        stringStructureMap.forEach((key, structure) -> {
            CompilationUnit compilationUnit = new CompilationUnit();
            compilationUnit.setPackageDeclaration(destPackage);
            ClassOrInterfaceDeclaration aClass = compilationUnit.addClass(structure.getClassName());
            aClass.addAnnotation(new MarkerAnnotationExpr(Mappable.class.getName()));
            for (FieldStructure fieldStructure : structure.getFieldStructures()) {
                String fieldName = "f" + fieldStructure.getName();
                FieldDeclaration field = aClass.addField("java.lang.String", fieldName, Modifier.PRIVATE);
                NormalAnnotationExpr annotation = new NormalAnnotationExpr();
                annotation.setName(Field.class.getName());
                annotation.addPair("name", new StringLiteralExpr(fieldStructure.getName()));
                annotation.addPair("begin", String.valueOf(fieldStructure.getStart()));
                annotation.addPair("end", String.valueOf(fieldStructure.getEnd()));
                field.addAnnotation(annotation);
            }

            for (FieldStructure fieldStructure : structure.getFieldStructures()) {
                MethodDeclaration getter = aClass.addMethod("get" + capitalize(fieldStructure.getName()), Modifier.PUBLIC);
                getter.setType("java.lang.String");
                BlockStmt getterBody = new BlockStmt();
                getterBody.addStatement(new ReturnStmt("this.f" + fieldStructure.getName()));
                getter.setBody(getterBody);


                MethodDeclaration setter = aClass.addMethod("set" + capitalize(fieldStructure.getName()), Modifier.PUBLIC);
                Parameter parameter = new Parameter();
                String parameterName = "p" + fieldStructure.getName();
                parameter.setName(parameterName);
                parameter.setType("java.lang.String");
                setter.addParameter(parameter);
                BlockStmt setterBody = new BlockStmt();
                AssignExpr assignExpr = new AssignExpr();
                assignExpr.setOperator(AssignExpr.Operator.ASSIGN);
                assignExpr.setTarget(new NameExpr("this.f" + fieldStructure.getName()));
                assignExpr.setValue(new NameExpr(parameterName));
                setterBody.addStatement(assignExpr);

                setter.setBody(setterBody);


            }
            try {

                FileWriter fileWriter = new FileWriter(new File(destDirectory, structure.getClassName() + ".java"));
                fileWriter.write(compilationUnit.toString());
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
