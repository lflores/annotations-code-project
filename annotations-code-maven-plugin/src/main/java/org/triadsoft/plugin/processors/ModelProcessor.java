package org.triadsoft.plugin.processors;

import com.google.auto.service.AutoService;
import org.triadsoft.plugin.processors.annotations.Builder;
import org.triadsoft.plugin.processors.annotations.Constructor;
import org.triadsoft.plugin.processors.annotations.Data;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(value = {
        "org.triadsoft.plugin.annotations.Constructor",
        "org.triadsoft.plugin.annotations.Data",
        "org.triadsoft.plugin.annotations.Builder"
})
@AutoService(Processor.class)
public class ModelProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        for (Element element : roundEnv.getElementsAnnotatedWith(Data.class)) {
            boolean hasBuilder = Objects.nonNull(element.getAnnotation(Builder.class));
            boolean hasConstructor = Objects.nonNull(element.getAnnotation(Constructor.class));
            this.generateImplementation(element, true, hasBuilder, hasConstructor);
        }
        return true;
    }

    private void generateImplementation(Element classElement, boolean hasData, boolean hasBuilder, boolean hasConstructor) {
        String className = classElement.getSimpleName().toString() + "Impl";
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(
                    processingEnv.getFiler().createSourceFile(className).openWriter());
            this.generateClassHeader(writer, classElement);
            if (hasConstructor || hasBuilder) {
                this.generateConstructor(writer, classElement);
            }
            if (hasData) {
                this.generateData(writer, classElement);
            }
            if (hasBuilder) {
                this.generateBuilderFile(writer, classElement);
            }
            this.generateClassEnd(writer, classElement);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateData(PrintWriter writer, Element classElement) {
        List<? extends Element> fields = classElement.getEnclosedElements()
                .stream().filter(e -> ElementKind.FIELD.equals(e.getKind()))
                .collect(Collectors.toList());
        fields.forEach(field -> {
                    String type = field.asType().toString();
                    String fieldName = field.getSimpleName().toString();
                    writer.println(String.format("\tpublic void set%s(%s %s){",
                            fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1),
                            type, fieldName));
                    writer.println(String.format("\t\tthis.%s=%s;", fieldName, fieldName));
                    writer.println("\t}");
                }
        );

        fields.forEach(field -> {
                    String type = field.asType().toString();
                    String fieldName = field.getSimpleName().toString();
                    writer.println(String.format("\tpublic %s get%s(){",
                            type, fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)));
                    writer.println(String.format("\t\treturn this.%s;", fieldName));
                    writer.println("\t}");
                }
        );
    }

    private void generateConstructor(PrintWriter writer, Element classElement) {
        String className = classElement.getSimpleName().toString() + "Impl";
        List<? extends Element> fields = classElement.getEnclosedElements()
                .stream().filter(e -> ElementKind.FIELD.equals(e.getKind()))
                .collect(Collectors.toList());
        // Constructor params
        writer.println(String.format("\tpublic %s(", className));
        List<String> fieldArray = fields
                .stream()
                .map(field -> {
                    String type = field.asType().toString();
                    return String.format("\t\t%s %s", type, field.getSimpleName().toString());
                }).collect(Collectors.toList());
        writer.println(String.join(",\n", fieldArray));
        writer.println("\t){");
        fields.forEach(field -> {
            writer.println(String.format("\t\tthis.%s = %s;", field.getSimpleName().toString(), field.getSimpleName().toString()));
        });
        writer.println("\t}");
    }

    private void generateBuilderFile(PrintWriter printWriter, Element element) {
        String className = element.getSimpleName().toString() + "Impl";
        String builderName = className + "Builder";
        List<? extends Element> fields = element.getEnclosedElements()
                .stream().filter(e -> ElementKind.FIELD.equals(e.getKind()))
                .collect(Collectors.toList());

        printWriter.println(String.format("\tpublic static %s builder() {\n" +
                "\t\treturn new %s();\n" +
                "\t}", builderName, builderName));

        printWriter.println(String.format("\tpublic static class %s {", builderName));
        fields.forEach(field ->
                printWriter.print(String.format("\t\tprivate %s %s;\n", field.asType().toString(), field.getSimpleName())
                )
        );

        printWriter.println();
        fields.forEach(field ->
                printWriter.println(String.format("\t\tpublic %s %s(%s value) {\n" +
                                "\t\t\t%s = value;\n" +
                                "\t\t\treturn this;\n" +
                                "\t\t}", builderName, field.getSimpleName(),
                        field.asType().toString(), field.getSimpleName())
                )
        );

        printWriter.println(String.format("\t\tpublic %s build() {\n" +
                        "\t\t\treturn new %s(%s);\n" +
                        "\t\t}", className, className,
                fields.stream().map(Element::getSimpleName)
                        .collect(Collectors.joining(", ")))
        );
        printWriter.println("\t}");
    }

    private void generateClassEnd(PrintWriter writer, Element classElement) {
        writer.println("}");
    }

    private void generateClassHeader(PrintWriter writer, Element classElement) {
        String packageName = classElement.getEnclosingElement().toString();
        String interfaceName = classElement.getSimpleName().toString();
        String classNameImpl = classElement.getSimpleName().toString() + "Impl";

        writer.println(String.format("package %s;\n", packageName));
        writer.println("/**\n" +
                " * Generated by annotation-code-generator\n" +
                " */");
        writer.println(String.format("public class %s implements %s{", classNameImpl, interfaceName));
        List<? extends Element> fields = classElement.getEnclosedElements()
                .stream().filter(e -> ElementKind.FIELD.equals(e.getKind()))
                .collect(Collectors.toList());
        fields.forEach(field ->
                writer.print(String.format("\tprivate %s %s;\n", field.asType().toString(), field.getSimpleName())
                )
        );
    }
}
