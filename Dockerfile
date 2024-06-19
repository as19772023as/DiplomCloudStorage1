FROM openjdk:17-jdk-slim

COPY target/DiplomCloudStorage1-1.0-SNAPSHOT.jar diplomproject1.jar

EXPOSE 5050

ENTRYPOINT ["java","-jar","diplomproject1.jar"]