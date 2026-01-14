# Use official OpenJDK 17 image as base
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and src folder
COPY pom.xml .
COPY src ./src

# Install Maven and build the application
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Set entrypoint to run your jar
ENTRYPOINT ["java","-jar","target/java-application-0.0.1-SNAPSHOT.jar"]
