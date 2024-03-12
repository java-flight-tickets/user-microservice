FROM openjdk:17.0.2
COPY target/springPlanes.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]