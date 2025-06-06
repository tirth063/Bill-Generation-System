# Start with a JDK 17 Alpine image for building
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy maven files first for better layer caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application with minimal footprint
RUN ./mvnw package -DskipTests -Pprod

# Use JRE Alpine for runtime (smaller image)
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Install only curl for healthchecks (skip bash to save space)
RUN apk add --no-cache curl

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring

# Set JVM options to limit memory usage for free tier
ENV JAVA_OPTS="-Xmx88m -Xms64m -XX:+UseSerialGC -XX:MaxRAM=128m -XX:+UseContainerSupport -XX:+ExitOnOutOfMemoryError"

# Copy the built jar from the builder stage
COPY --from=builder /app/target/IMS-1.jar app.jar

# Set ownership to the non-root user
RUN chown -R spring:spring /app
USER spring:spring

# Make port 8080 available outside the container
EXPOSE 8080

# Add healthcheck (using lightweight curl approach)
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Set the entrypoint with memory constraints
ENTRYPOINT exec java $JAVA_OPTS -jar /app/app.jar