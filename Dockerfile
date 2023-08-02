FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/plants_server.jar /usr/local/lib/plants_server.jar
ENV JAVA_TOOL_OPTIONS "-XX:MaxRAM=200000000", "-Xmx200mb"
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/plants_server.jar"]
