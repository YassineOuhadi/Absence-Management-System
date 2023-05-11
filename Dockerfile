FROM maven:3.8.3-openjdk-17 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package

FROM openjdk:17-jdk-alpine
COPY --from=build /workspace/target/*.jar attendance_api.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","attendance_api.jar"]