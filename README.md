# LearningApp: Microservices-Based Quiz Application

## **How to Run the Project**

### **1. Build the Project**
To build the project, navigate to each service directory (`pg3402/loginservice`, `pg3402/flashcardservice`, `pg3402/quizservice`, `pg3402/scoreservice`, `pg3402/gateway`) and run the following command in the terminal:

```bash
mvn clean install
```

### **2. Start the Project**
Once all services are built, navigate to the `pg3402/docker` folder and run the following commands:

```bash
docker-compose down
docker-compose up --build -d
```

This will start all services as Docker containers.

### **3. Monitor Services**
- Open Consul to check the status of all services:  
  [http://localhost:8500/ui/dc1/services](http://localhost:8500/ui/dc1/services)  
  Wait until all services are displayed as green in Consul:
  - `gateway`
  - `loginservice`
  - `flashcardservice`
  - `scoreservice`
  - `quizservice`
  - `consul`

### **4. H2 Database Console**
Each service has an H2 database accessible through the following links:

| Service           | URL                                           | JDBC URL                | Username | Password |
|--------------------|-----------------------------------------------|-------------------------|----------|----------|
| Login Service      | [http://localhost:8084/h2-console](http://localhost:8084/h2-console) | `jdbc:h2:file:~/learningapp` | `user`   | *(none)* |
| Quiz Service       | [http://localhost:8081/h2-console](http://localhost:8081/h2-console) | `jdbc:h2:file:~/learningapp` | `user`   | *(none)* |
| Flashcard Service  | [http://localhost:8087/h2-console](http://localhost:8087/h2-console) | `jdbc:h2:file:~/learningapp` | `user`   | *(none)* |
| Score Service      | [http://localhost:8082/h2-console](http://localhost:8082/h2-console) | `jdbc:h2:file:~/learningapp` | `user`   | *(none)* |

### **5. RabbitMQ**
RabbitMQ is used for asynchronous communication between services.  
Access the RabbitMQ Dashboard at:  
[http://localhost:15672/](http://localhost:15672/)  
Username: `guest`  
Password: `guest`

---

## **Architecture**
The system consists of the following microservices:  
1. **Login Service** (Port: 8084)  
2. **Quiz Service** (Port: 8081)  
3. **Flashcard Service** (Port: 8087)  
4. **Score Service** (Port: 8082)  
5. **Gateway** (Port: 8080)  

Communication types:  
- Synchronous communication: REST calls via Gateway.  
- Asynchronous communication: RabbitMQ for message passing.  

![System Architecture](link-to-architecture-diagram)  

---

## **User Stories and API Endpoints**
Below is a list of functionalities and how to test them using Postman:

### **1. Login/Registration**
**Scenario:** As a student, I want to register and log in to access the app.

- **Register**  
  Method: `POST`  
  URL: `http://localhost:8084/api/users/register`  
  Body:  
  ```json
  {
      "email": "kally@gmail.com",
      "rawPassword": "secret123",
      "firstName": "kally",
      "lastName": "kai"
  }
  ```

- **Login**  
  Method: `POST`  
  URL: `http://localhost:8084/api/users/login`  
  Body:  
  ```json
  {
      "email": "kally@gmail.com",
      "rawPassword": "secret123"
  }
  ```

---

### **2. Connect with Friends**
**Scenario:** As a user, I want to connect with friends to view and compare scores.

- **View All Users**  
  Method: `GET`  
  URL: `http://localhost:8084/api/users`

- **Search for a Specific User**  
  Method: `GET`  
  URL: `http://localhost:8084/api/users/search/kally/kai`

---

### **3. Take Quiz**
**Scenario:** As a student, I want to take quizzes and receive feedback on my answers.

- **View All Available Quizzes**  
  Method: `GET`  
  URL: `http://localhost:8081/quizapi`

- **Start a Quiz**  
  Method: `POST`  
  URL: `http://localhost:8081/quizapi/1/start`  
  Body:  
  ```json
  {
      "email": "kally@gmail.com"
  }
  ```

- **Submit Answer**  
  Method: `POST`  
  URL: `http://localhost:8081/quizapi/1/answer`  
  Body:  
  ```json
  {
      "email": "kally@gmail.com",
      "questionId": "1",
      "answer": "4"
  }
  ```

---

### **4. Admin - Create Quiz**
**Scenario:** As an admin, I want to create quizzes for students.

- **Create Quiz**  
  Method: `POST`  
  URL: `http://localhost:8081/quizapi`  
  Body:  
  ```json
  {
    "email": "admin@gmail.com",
    "title": "Science Grade 9",
    "chapter": "Fungi and Leaves",
    "subject": "Science",
    "questions": [
      {
        "questionId": 1,
        "questionText": "What is a fungus?",
        "correctAnswer": "A type of fungal structure"
      }
    ]
  }
  ```

- **Find Quiz by ID**  
  Method: `GET`  
  URL: `http://localhost:8081/quizapi/quiz/1`

---

### **5. View Scores**
**Scenario:** As a student, I want to view my own and others' quiz scores.

- **View Scores for All Users**  
  Method: `GET`  
  URL: `http://localhost:8082/scores`

- **View Score for a Specific User**  
  Method: `POST`  
  URL: `http://localhost:8082/scores/email`  
  Body:  
  ```json
  {
    "email": "kally@gmail.com"
  }
  ```
