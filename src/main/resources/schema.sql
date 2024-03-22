DROP TABLE IF EXISTS Recipe CASCADE;
DROP TABLE IF EXISTS Member CASCADE;

CREATE TABLE Member
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Recipe
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    ingredients TEXT         NOT NULL,
    author_id   INT          NOT NULL,
    FOREIGN KEY (author_id) REFERENCES Member (id)
);