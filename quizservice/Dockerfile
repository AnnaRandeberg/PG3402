FROM openjdk:21
COPY ./target/quizservice-0.0.1-SNAPSHOT.jar /usr/src/quizservice/
WORKDIR /usr/src/quizservice/
EXPOSE 8081
CMD ["java", "-jar", "quizservice-0.0.1-SNAPSHOT.jar"]
