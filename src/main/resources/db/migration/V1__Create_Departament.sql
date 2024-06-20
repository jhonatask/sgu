CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE department (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);