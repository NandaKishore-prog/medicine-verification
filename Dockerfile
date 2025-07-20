FROM eclipse-temurin:21-jdk

WORKDIR /app

# Install Python, pip, and required shared libraries
RUN apt-get update && \
    apt-get install -y python3 python3-pip libgl1 libglib2.0-0 && \
    rm -rf /var/lib/apt/lists/*

# Copy and install Python dependencies
COPY requirements.txt .
RUN pip3 install --no-cache-dir --break-system-packages -r requirements.txt

# Copy project files
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src
COPY src/main/PythonFiles /app/scripts

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/Medicine-Project-0.0.1-SNAPSHOT.jar"]
