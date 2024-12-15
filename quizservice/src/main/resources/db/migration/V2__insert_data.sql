INSERT INTO quiz (quiz_id, title, chapter, subject)
SELECT 1, 'Matte 8. klasse', 'Algebra', 'Mattematikk'
WHERE NOT EXISTS (
    SELECT 1 FROM quiz WHERE quiz_id = 1
);

INSERT INTO questions (question_id, quiz_id, question_text, answer)
SELECT 1, 1, 'Hva er 2 + 2?', '4'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_id = 1
);

INSERT INTO questions (question_id, quiz_id, question_text, answer)
SELECT 2, 1, 'Hva er 3 * 3?', '9'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_id = 2
);
