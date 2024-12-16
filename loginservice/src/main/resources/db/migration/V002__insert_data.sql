INSERT INTO users (user_id, email, password_hash, first_name, last_name, role)
SELECT 1, 'admin@gmail.com', 'secret', 'Admin', 'User', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@gmail.com'
);

INSERT INTO users (user_id, email, password_hash, first_name, last_name, role)
SELECT 2, 'lisa@student.com', 'secret123', 'lisa', 'lo', 'STUDENT'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'lisa@student.com'
);