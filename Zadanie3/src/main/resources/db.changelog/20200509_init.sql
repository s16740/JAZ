CREATE TABLE users
(
    id BIGINT NOT NULL,
    username VARCHAR (50) NOT NULL,
    password VARCHAR (255) NOT NULL,
    firstName VARCHAR (50) NOT NULL,
    lastName VARCHAR (50) NOT NULL,
    birthDate DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE hibernate_sequence;
