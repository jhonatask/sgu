-- Adicionar constraint de unicidade para CPF/CNPJ
-- Primeiro, vamos limpar dados duplicados (manter apenas o mais recente)
WITH duplicates AS (
    SELECT id, 
           ROW_NUMBER() OVER (PARTITION BY cpforcnpj ORDER BY datacadastro DESC) as rn
    FROM users
)
DELETE FROM users 
WHERE id IN (
    SELECT id FROM duplicates WHERE rn > 1
);

-- Agora adicionar a constraint de unicidade
ALTER TABLE users ADD CONSTRAINT UK_users_cpforcnpj UNIQUE (cpforcnpj);
