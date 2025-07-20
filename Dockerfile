# Use Eclipse Temurin JDK 21 base image
FROM eclipse-temurin:21-jdk

# Set working directory inside the container
WORKDIR /app

# Install Python and pip
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

# Copy Python requirements and install them
COPY requirements.txt .
RUN pip3 install --no-cache-dir -r requirements.txt

# Copy Maven wrapper and project files
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Pre-download Maven dependencies (optional optimization)
RUN ./mvnw dependency:go-offline -B

# Copy Java source code
COPY src ./src

# Copy Python scripts to known path in container
COPY src/main/PythonFiles /app/scripts

# Build Spring Boot application (skipping tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/Medicine-Project-0.0.1-SNAPSHOT.jar"]
