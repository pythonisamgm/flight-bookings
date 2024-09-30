#Imagen que descargara de Docker Hub con la version correcta de Java
FROM openjdk:21-jdk-slim

#Informar en que puerto se expone el contenedor (es a modo informativo)
EXPOSE 8080

#Crear un directorio raiz de nuestro contenedor
WORKDIR /root

#Copiar el código fuente (*.jar) dentro del directorio raiz que creamos para el contenedor
COPY target/demo-0.0.1-SNAPSHOT.jar /src/app.jar

#Levantar nuestra aplicación cuando el contenedor inicie
ENTRYPOINT ["java","-jar","/src/app.jar"]