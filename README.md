# Annotations Code Generator   

El siguiente proyecto es un ejemplo de como generar un plugin de maven y como poder utilizar el plugin y aprovechar las
funcionalidades generadas en él.

El plugin es bastante sencillo pero muestra los dos aspectos más importantes que se pueden usar con [Maven](https://maven.apache.org).
El ejemplo no busca ser exhaustivo sino animar a aquellos que quieran incursionar y que muchas veces por miedo o desconocimiento 
no se animaron, como me pasó a mi.

Este repositorio está estructurado para poder bajar el código y poder ejecutar para ver los ejemplos en funcionamiento. 
Si desean crear un repositorio similar 

## Instalación del plugin

Una vez que tengan descagado el repositorio podrán generar el plugin y podrán usarlo para ver los resultados.

Con el siguiente comando en el directorio raíz de éste proyecto podrán generar una versión del plugin y podrán instalarlo
en su repositorio local para su posterior uso.

```bash
mvn clean install -f annotations-code-maven-plugin/pom.xml
```

Con ésto podrán generar el plugin y lo dejarán instalado para poder usarlo como vemos más adelante.

## Ejecutar el plugin

Una vez que el plugin se encuentra instalado podremos usarlo de la siguiente manera.
Tenemos dos partes visibles para probar o ejecutar por linea de comando.

```bash
mvn org.triadsoft.plugin:annotations-code-maven-plugin:version
```
```bash
mvn org.triadsoft.plugin:annotations-code-maven-plugin:property-helper
```

## Ejecutar el uso del plugin

```bash
mvn clean package -f annotations-code-user/pom.xml
```

## Como se creo éste proyecto

Aquí encontrarán una mini guía de como se creo y se estructuró éste proyecto para que sirva de base para futuros proyectos.


### Crear el proyecto base

```bash
mvn archetype:generate \
-DarchetypeGroupId=org.codehaus.mojo.archetypes \
-DarchetypeArtifactId=pom-root \
-DarchetypeVersion=1.1 \
-DgroupId=org.triadsoft.plugin \
-DartifactId=annotations-code-gen-maven-plugin
```

### Crear el módulo para el plugin

```bash
mvn archetype:generate \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DgroupId=org.triadsoft.plugin \
-DartifactId=annotations-code-generator
```

### Crear el módulo de uso

```bash
mvn archetype:generate \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DgroupId=org.triadsoft.plugin \
-DartifactId=annotations-code-user
```

### Agregar properties para el compilador y el encoding
maven Source option 5 is no longer supported. Use 7 or later.

```xml
<properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
</properties>
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