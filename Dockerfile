FROM openjdk:11

WORKDIR /app

ENV JAVA_TOOL_OPTIONS "-XX:MaxRAM=250000000"

ADD target/plants_server.jar plants_server.jar

ENTRYPOINT ["java","-jar","plants_server.jar"]