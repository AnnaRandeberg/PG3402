FROM openjdk:21
COPY ./target/loginservice-0.0.1-SNAPSHOT.jar /usr/src/loginservice/
WORKDIR /usr/src/loginservice/
EXPOSE 8084
CMD ["java", "-jar", "loginservice-0.0.1-SNAPSHOT.jar"]
