CREATE TABLE users (
                       id VARCHAR PRIMARY KEY,
                       email VARCHAR NOT NULL,
                       username VARCHAR NOT NULL UNIQUE,
                       password VARCHAR NOT NULL,
                       first_name VARCHAR NOT NULL,
                       last_name VARCHAR,
                       company_name VARCHAR,
);