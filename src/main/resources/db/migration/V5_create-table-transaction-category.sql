CREATE TABLE transaction_category(
    id VARCHAR PRIMARY KEY,
    id_transaction VARCHAR NOT NULL,
    id_category VARCHAR NOT NULL,
    FOREIGN KEY (id_transaction) REFERENCES transactions (id) ON DELETE CASCADE,
    FOREIGN KEY (id_category) REFERENCES categories (id) ON DELETE CASCADE
);