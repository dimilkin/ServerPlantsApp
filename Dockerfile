FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

ENV JAVA_TOOL_OPTIONS "-XX:MaxRAM=250"

CMD ["./mvnw", "spring-boot:run"]