CREATE TABLE categories (
                       id VARCHAR PRIMARY KEY,
                       name VARCHAR NOT NULL,
                       description VARCHAR NOT NULL,
                       id_transaction VARCHAR NOT NULL,
                       FOREIGN KEY id_transaction REFERENCES transactions (id) ON DELETE RESTRICT ON UPDATE CASCADE
);