CREATE TABLE IF NOT EXISTS users (
user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(255) NOT NULL UNIQUE,
password_hash VARCHAR(255) NOT NULL,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
role VARCHAR(255)
);

INSERT INTO users (email, password_hash, first_name, last_name, role)
SELECT 'admin@gmail.com', 'secret', 'Admin', 'User', 'ADMIN'
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@gmail.com'
);

INSERT INTO users (email, password_hash, first_name, last_name, role)
SELECT 'lisa@student.com', 'secret123', 'lisa', 'lo', 'STUDENT'
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'lisa@student.com'
);
