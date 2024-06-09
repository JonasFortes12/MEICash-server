CREATE TABLE categories (
                            id VARCHAR PRIMARY KEY,
                            name VARCHAR NOT NULL,
                            color VARCHAR NOT NULL,
                            user_id VARCHAR NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
);