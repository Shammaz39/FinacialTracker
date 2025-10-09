FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
# Give execute permission to mvnw and use mvn directly
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/fintrack.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]