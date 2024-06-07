CREATE TABLE user_transaction(
    id VARCHAR PRIMARY KEY,
    id_user VARCHAR NOT NULL,
    id_transaction VARCHAR NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (id_transaction) REFERENCES transactions (id) ON DELETE CASCADE
);