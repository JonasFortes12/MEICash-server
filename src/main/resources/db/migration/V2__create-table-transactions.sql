CREATE TABLE transactions (
                       id VARCHAR PRIMARY KEY,
                       timestamp TIMESTAMP NOT NULL,
                       type VARCHAR NOT NULL,
                       value DOUBLE PRECISION NOT NULL,
                       description TEXT,
                       id_user VARCHAR NOT NULL,
                        id_category VARCHAR NOT NULL
                       FOREIGN KEY id_user REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
                       FOREIGN KEY id_category REFERENCES category (id) ON DELETE RESTRICT ON UPDATE CASCADE
);