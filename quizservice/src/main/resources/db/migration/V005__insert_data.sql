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
