FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the pre-built jar from target folder
COPY target/aeroparker-registration-0.0.1.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
