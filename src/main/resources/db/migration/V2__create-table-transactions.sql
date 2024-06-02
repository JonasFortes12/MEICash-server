CREATE TABLE transactions (
                       id VARCHAR PRIMARY KEY,
                       timestamp TIMESTAMP NOT NULL,
                       type VARCHAR NOT NULL UNIQUE,
                       category VARCHAR NOT NULL,
                       value DOUBLE PRECISION NOT NULL,
                       description TEXT
);