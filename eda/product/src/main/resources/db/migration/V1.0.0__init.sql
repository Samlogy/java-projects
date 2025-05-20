create table if not exists product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    quantity INT,
    price REAL,
    category VARCHAR(255)
);