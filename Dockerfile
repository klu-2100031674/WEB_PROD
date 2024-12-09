FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/Fashion_Designs-0.0.1-SNAPSHOT.jar Fashion_Design.jar
ENTRYPOINT ["java","-jar","/Fashion_Designs.jar"]
