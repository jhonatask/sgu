ALTER TABLE users ADD COLUMN telefone VARCHAR(15) NOT NULL;
ALTER TABLE users ADD COLUMN dataCadastro TIMESTAMP NOT NULL DEFAULT NOW();