FROM eclipse-temurin:21-jdk

WORKDIR /app

# Install Python, pip, and OpenCV dependency
RUN apt-get update && \
    apt-get install -y python3 python3-pip libgl1 && \
    rm -rf /var/lib/apt/lists/*

# Copy and install Python dependencies
COPY requirements.txt .
RUN pip3 install --no-cache-dir --break-system-packages -r requirements.txt

# Copy Maven and Spring Boot files
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src
COPY src/main/PythonFiles /app/scripts

RUN ./mvnw clean package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/Medicine-Project-0.0.1-SNAPSHOT.jar"]
