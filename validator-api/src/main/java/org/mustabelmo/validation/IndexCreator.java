package org.mustabelmo.validation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import org.mustabelmo.FieldStructure;
import org.mustabelmo.Structure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

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
        Map<String, Structure> stringStructureMap = readFile("D:\\0001_PERSO\\0003_CODE\\PlateFileExtractor\\validator-examples\\src\\main\\resources\\indexer.csv");
        stringStructureMap.forEach((key, structure) -> {
            CompilationUnit compilationUnit = new CompilationUnit();
            ClassOrInterfaceDeclaration aClass = compilationUnit.addClass(structure.getClassName());
            aClass.addAnnotation(new MarkerAnnotationExpr("org.mustabelmo.validation.annotation.Mappable"));
            for (FieldStructure fieldStructure : structure.getFieldStructures()) {
                FieldDeclaration field = aClass.addField("String", "f"+fieldStructure.getName(), Modifier.PRIVATE);
                NormalAnnotationExpr annotation = new NormalAnnotationExpr();
                annotation.setName("org.mustabelmo.validation.annotation.Field");
                annotation.addPair("name", new StringLiteralExpr(fieldStructure.getName()));
                annotation.addPair("begin", String.valueOf(fieldStructure.getStart()));
                annotation.addPair("end", String.valueOf(fieldStructure.getEnd()));
                field.addAnnotation(annotation);
            }
            try {
                FileWriter fileWriter = new FileWriter(new File("D:\\0001_PERSO\\0003_CODE\\PlateFileExtractor\\validator-examples\\target\\generated-sources\\validation",
                        structure.getClassName() + ".java"));
                fileWriter.write(compilationUnit.toString());
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
