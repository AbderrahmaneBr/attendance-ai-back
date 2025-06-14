# === BUILD STAGE ===
# You can leave this as Alpine, or change it too for consistency if you prefer.
# Leaving it as Alpine here:
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file first to leverage Docker's caching
COPY pom.xml .

# Download dependencies (only if pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Package the Spring Boot application
RUN mvn clean package -DskipTests

# === RUNTIME STAGE ===
# *** THIS IS THE LINE TO CHANGE ***
# Replace 'eclipse-temurin:17-jre-alpine' with a Debian/Ubuntu-based alternative.
FROM eclipse-temurin:17-jre-focal
# OR alternatively:
# FROM openjdk:17-jre-slim-buster # <-- Another good option (Debian 10 'Buster' based)
# OR even:
# FROM eclipse-temurin:17-jre-jammy # (Ubuntu 22.04 LTS based)

# Set the working directory
WORKDIR /app

# Expose the port your Spring Boot app listens on (default is 8086)
EXPOSE 8080

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Define the entry point for the container
ENTRYPOINT ["java","-jar","/app/app.jar"]

# Optional: Add Spring Boot specific arguments for better startup
# ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]