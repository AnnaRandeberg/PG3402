FROM openjdk:21
COPY ./target/scoreservice-0.0.1-SNAPSHOT.jar /usr/src/scoreservice/
WORKDIR /usr/src/scoreservice/
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "scoreservice-0.0.1-SNAPSHOT.jar"]
