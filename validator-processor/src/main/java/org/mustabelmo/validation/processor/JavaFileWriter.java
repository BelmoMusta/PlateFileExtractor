package org.mustabelmo.validation.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.Writer;

public class JavaFileWriter {
    public static void writeJavaClass(ClassWrapper classWrapper, ProcessingEnvironment processingEnv) {
        try {
            final JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(classWrapper.getGeneratedClassName());
            final Writer writer = builderFile.openWriter();
            writer.write(classWrapper.getCompilationUnit().toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
