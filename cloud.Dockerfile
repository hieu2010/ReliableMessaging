FROM openjdk:11.0.10-jdk-slim-buster

EXPOSE 8080

COPY /cloud-server-mock/target/cloud-server-mock-0.0.1-SNAPSHOT.jar cloud-server-mock.jar

ENTRYPOINT ["java", "-jar", "/cloud-server-mock.jar"]
