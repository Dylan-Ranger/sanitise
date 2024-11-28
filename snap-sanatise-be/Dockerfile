FROM openjdk:22-jdk-slim

WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline -B

COPY . .

EXPOSE 8080

ENV JAVA_OPTS="-Dspring.devtools.restart.enabled=true"

CMD ["./mvnw", "spring-boot:run"]
