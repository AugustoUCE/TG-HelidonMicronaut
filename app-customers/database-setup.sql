-- Script para crear la tabla customer en PostgreSQL
-- Base de datos: postgres
-- Usuario: postgres
-- Password: postgres

-- Crear tabla customer
CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE
);

-- Crear tabla purcharse_order (opcional, para referencia)
CREATE TABLE IF NOT EXISTS purcharse_order (
    id SERIAL PRIMARY KEY,
    placed_on DATE,
    delivered_on DATE,
    total NUMERIC(10, 2),
    customer_id INTEGER,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- Insertar datos de prueba
INSERT INTO customer (first_name, last_name, email) VALUES
('Juan', 'Pérez', 'juan.perez@email.com'),
('María', 'González', 'maria.gonzalez@email.com'),
('Juan', 'López', 'juan.lopez@email.com'),
('Carlos', 'Martínez', 'carlos.martinez@email.com'),
('Ana', 'Rodríguez', 'ana.rodriguez@email.com'),
('Pedro', 'Sánchez', 'pedro.sanchez@email.com')
ON CONFLICT (email) DO NOTHING;

-- Verificar que los datos se insertaron correctamente
SELECT * FROM customer;
