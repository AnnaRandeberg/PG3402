CREATE TABLE IF NOT EXISTS flashcards (
flashcard_id BIGINT AUTO_INCREMENT PRIMARY KEY,
question_text VARCHAR(255) NOT NULL,
answer VARCHAR(255) NOT NULL
);

INSERT INTO flashcards (question_text, answer)
SELECT 'Hva er 5 + 3?', '8'
    WHERE NOT EXISTS (
    SELECT 1 FROM flashcards WHERE question_text = 'Hva er 5 + 3?'
);

INSERT INTO flashcards (question_text, answer)
SELECT 'Hva er 12 - 7?', '5'
    WHERE NOT EXISTS (
    SELECT 1 FROM flashcards WHERE question_text = 'Hva er 12 - 7?'
);

INSERT INTO flashcards (question_text, answer)
SELECT 'Hva er verdien av x i ligningen x^2 = 16?', '4 eller -4'
    WHERE NOT EXISTS (
    SELECT 1 FROM flashcards WHERE question_text = 'Hva er verdien av x i ligningen x^2 = 16?'
);

INSERT INTO flashcards (question_text, answer)
SELECT 'Hvis 3x - 7 = 8, hva er x?', '5'
    WHERE NOT EXISTS (
    SELECT 1 FROM flashcards WHERE question_text = 'Hvis 3x - 7 = 8, hva er x?'
);

INSERT INTO flashcards (question_text, answer)
SELECT 'Hva er definisjonen av en primtall?', 'Et tall større enn 1 som kun er delelig med 1 og seg selv'
    WHERE NOT EXISTS (
    SELECT 1 FROM flashcards WHERE question_text = 'Hva er definisjonen av en primtall?'
);

INSERT INTO flashcards (question_text, answer)
SELECT 'Hva kalles en firkant hvor alle sider og vinkler er like?', 'Kvadrat'
    WHERE NOT EXISTS (
    SELECT 1 FROM flashcards WHERE question_text = 'Hva kalles en firkant hvor alle sider og vinkler er like?'
);
