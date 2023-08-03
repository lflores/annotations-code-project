# Annotations Code Generator   

## Crear el proyecto base

```bash
mvn archetype:generate \
-DarchetypeGroupId=org.codehaus.mojo.archetypes \
-DarchetypeArtifactId=pom-root \
-DarchetypeVersion=1.1 \
-DgroupId=org.perficient \
-DartifactId=annotations-code-gen-maven-plugin
```

## Crear el módulo para el plugin

```bash
mvn archetype:generate \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DgroupId=org.perficient.plugin \
-DartifactId=annotations-code-generator
```

## Crear el módulo de uso

```bash
mvn archetype:generate \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DgroupId=org.perficient.plugin \
-DartifactId=annotations-code-user
```

## Agregar properties para el compilador y el encoding
maven Source option 5 is no longer supported. Use 7 or later.

```xml
<properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
</properties>
```

## Ejecutar el plugin

```bash
mvn org.perficient.plugin:annotations-code-generator:version
```

## Ejecutar el uso del plugin

```bash
mvn package -f annotations-code-user/pom.xml
```

## Recursos

### Apache Maven Proyect

https://maven.apache.org/plugin-developers/index.html

### Build a Custom Maven Plugin

https://www.youtube.com/watch?v=wHX4j0z-sUU&list=PLVaxpbA7ffz-X1HZsBjalwDM-eMRm5tPl&index=2

### How to Build a Maven Plugin
https://developer.okta.com/blog/2019/09/23/tutorial-build-a-maven-plugin

### Java Annotation Processing and Creating a Builder

https://www.baeldung.com/java-annotation-processing-builder

### Annotation Processing in Java | Create your own Annotation Processor | Geekific
https://www.youtube.com/watch?v=ja4is9oq37k