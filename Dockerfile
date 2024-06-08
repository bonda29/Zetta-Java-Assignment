# Use a multi-stage build to create a clean final image
FROM openjdk:17-jdk-slim AS build

# Update package lists and Install Maven
RUN apt-get update && apt-get install -y maven

COPY pom.xml ./
COPY src src
RUN mvn dependency:resolve
RUN mvn package

FROM openjdk:17-jdk-slim
WORKDIR demo

# Expose port 8080
EXPOSE 8080

COPY --from=build target/*.jar demo.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]