CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE diretorio_csv (
        id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
        caminho TEXT NOT NULL
);
