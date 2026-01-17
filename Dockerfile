# Stage 1: Build using Maven + JDK
FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only pom.xml first to leverage cache for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the app with minimal JDK image
FROM eclipse-temurin:17-jdk AS runtime

WORKDIR /app

# Copy the JAR built in previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

