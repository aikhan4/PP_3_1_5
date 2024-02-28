DROP TABLE IF EXISTS users, roles, user_role;

-- Создание таблицы ролей
CREATE TABLE IF NOT EXISTS roles (
                                     id BIGINT NOT NULL AUTO_INCREMENT,
                                     rolename VARCHAR(255),
                                     PRIMARY KEY (id)
);

-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT NOT NULL AUTO_INCREMENT,
                                     age BIGINT,
                                     email VARCHAR(255),
                                     first_name VARCHAR(255),
                                     last_name VARCHAR(255),
                                     password VARCHAR(255),
                                     PRIMARY KEY (id)
);

-- Создание таблицы для связи пользователей и ролей
CREATE TABLE IF NOT EXISTS user_role (
                                         user_id BIGINT NOT NULL,
                                         role_id BIGINT NOT NULL,
                                         FOREIGN KEY (user_id) REFERENCES users (id),
                                         FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Вставка данных в таблицу пользователей
INSERT INTO users (age, email, first_name, last_name, password) VALUES (18, 'akasum99@gmail.com', 'ayhan', 'kasumov', '$2a$10$HjJb3/CCLCqMHzX6egTdnOcdPWUtFU8Y7DI/Fpdq4ZNmXn2/AS7YS');

-- Вставка данных в таблицу ролей
INSERT INTO roles (rolename) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

-- Вставка данных в таблицу user_role для связи пользователей и ролей
INSERT INTO user_role (user_id, role_id) VALUES (1, 1), (1, 2);