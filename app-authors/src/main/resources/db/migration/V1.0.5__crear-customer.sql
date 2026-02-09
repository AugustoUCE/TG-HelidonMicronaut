CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

INSERT INTO customer (first_name, last_name, email)
VALUES
    ('Juan', 'Pérez', 'juan.perez@example.com'),
    ('María', 'Gómez', 'maria.gomez@example.com'),
    ('Luis', 'Rodríguez', 'luis.rodriguez@example.com')
    ON CONFLICT (email) DO NOTHING;