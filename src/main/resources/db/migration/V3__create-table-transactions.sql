CREATE TABLE transactions (
                              id VARCHAR PRIMARY KEY,
                              title VARCHAR NOT NULL,
                              timestamp TIMESTAMP NOT NULL,
                              type VARCHAR NOT NULL,
                              value DOUBLE PRECISION NOT NULL,
                              description TEXT,
                              user_id VARCHAR NOT NULL,
                              category_id VARCHAR NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                              FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);