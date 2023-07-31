FROM openjdk:11

COPY --from=build /home/app/target/plants_server.jar /usr/local/lib/plants_server.jar

ENV JAVA_TOOL_OPTIONS "-XX:MaxRAM=250000000"

EXPOSE 8080

ENTRYPOINT ["java","-jar","plants_server.jar"]