FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/Fashion_Desigins-0.0.1-SNAPSHOT.jar Fashion_Desigins.jar
ENTRYPOINT ["java","-jar","/Fashion_Desigins.jar"]
