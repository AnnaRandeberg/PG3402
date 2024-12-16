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


INSERT INTO quiz (title, chapter, subject)
SELECT 'Matte 8. klasse', 'Algebra', 'Mattematikk'
WHERE NOT EXISTS (
    SELECT 1 FROM quiz WHERE title = 'Matte 8. klasse'
);


INSERT INTO questions (quiz_id, question_text, answer)
SELECT 1, 'Hva er 2 + 2?', '4'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_text = 'Hva er 2 + 2?');


INSERT INTO questions (quiz_id, question_text, answer)
SELECT 1, 'Hva er 3 * 3?', '9'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_text = 'Hva er 3 * 3?');
