CREATE TABLE IF NOT EXISTS quiz (
quiz_id BIGINT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
chapter VARCHAR(255),
subject VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS questions (
question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
quiz_id BIGINT NOT NULL,
question_text VARCHAR(255) NOT NULL,
answer VARCHAR(255),
FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id)
);


