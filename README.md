# 🏢 SGU - Sistema de Gerenciamento de Usuários

Sistema de gerenciamento de usuários desenvolvido com Spring Boot, seguindo as melhores práticas de segurança, arquitetura e performance.

## 🚀 **Características Principais**

- ✅ **CRUD completo** de usuários e departamentos
- ✅ **Autenticação JWT** com segurança robusta
- ✅ **Validação de dados** com mensagens centralizadas
- ✅ **Importação em massa** via arquivo CSV
- ✅ **Cache inteligente** para performance
- ✅ **Rate limiting** para proteção
- ✅ **Documentação automática** com Swagger
- ✅ **Testes unitários** e de integração

## 🏗️ **Arquitetura**

### **Clean Architecture / DDD**
```
src/main/java/br/com/jproject/sgu/
├── application/          # Controllers e Use Cases
├── domain/              # Entidades e regras de negócio
└── core/                # Configurações e segurança
```

### **Tecnologias**
- **Spring Boot 3.3.0** - Framework principal
- **Java 21** - Linguagem de programação
- **PostgreSQL 15** - Banco de dados
- **JWT** - Autenticação
- **Flyway** - Migrações
- **Docker** - Containerização
- **Maven** - Gerenciamento de dependências

## ⚡ **Início Rápido**

### **1. Pré-requisitos**
- Java 21+
- Maven 3.9+
- PostgreSQL 15+
- Docker (opcional)

### **2. Configuração**
```bash
# Clone o repositório
git clone <seu-repositorio>
cd sgu

# Configure as variáveis de ambiente
cp env.example .env
# Edite o arquivo .env com suas configurações
```

### **3. Execução**

#### **Opção 1: Docker Compose (Recomendado)**
```bash
docker-compose up -d
```

#### **Opção 2: Execução Local**
```bash
# Configure o banco de dados
createdb dbsgu

# Execute a aplicação
mvn spring-boot:run
```

### **4. Acessar a Aplicação**
- **API**: http://localhost:8080
- **Swagger**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/api/health

## 🔧 **Configuração de Ambiente**

### **Variáveis de Ambiente (.env)**
```bash
# Database
DB_URL=jdbc:postgresql://localhost:5432/dbsgu
DB_USERNAME=postgres
DB_PASSWORD=sua_senha

# JWT
JWT_SECRET=sua_chave_jwt_segura
JWT_EXPIRATION=86400000

# CORS
CORS_ORIGINS=http://localhost:3000,http://localhost:8080

# Logs
LOG_LEVEL=INFO
SHOW_SQL=false
```

## 📚 **Documentação da API**

### **Endpoints Principais**
- `POST /api/users` - Criar usuário
- `GET /api/users` - Listar usuários (paginado)
- `GET /api/users/{id}` - Buscar usuário por ID
- `PUT /api/users/{id}` - Atualizar usuário
- `DELETE /api/users/{id}` - Deletar usuário
- `POST /api/users/importar` - Importar CSV
- `POST /auth/login` - Autenticação

### **Autenticação**
```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "usuario@email.com", "password": "senha"}'

# Usar token
curl -H "Authorization: Bearer SEU_TOKEN" http://localhost:8080/api/users
```

## 🛡️ **Segurança**

### **Recursos Implementados**
- ✅ **JWT** com chave segura
- ✅ **Rate Limiting** (60 req/min, 1000 req/hora)
- ✅ **Validação robusta** de dados
- ✅ **CORS configurável**
- ✅ **Headers de segurança**
- ✅ **Senhas criptografadas** (BCrypt)

### **Validações**
- **Email único** no sistema
- **CPF/CNPJ único** no sistema
- **Senhas seguras** (mínimo 8 caracteres, maiúscula, minúscula, número, símbolo)
- **Telefone** no formato brasileiro
- **Email** com formato válido

## 🧪 **Testes**

```bash
# Executar todos os testes
mvn test

# Executar testes específicos
mvn test -Dtest=UserServiceTest
```

## 📊 **Monitoramento**

### **Health Checks**
- `GET /api/health` - Status básico
- `GET /api/health/detailed` - Status detalhado
- `GET /actuator/health` - Spring Boot Actuator

### **Logs**
- **Aplicação**: `logs/sgu-application.log`
- **Segurança**: `logs/sgu-security.log`
- **Configuração**: `logback-spring.xml`

## 🐳 **Docker**

### **Desenvolvimento**
```bash
docker-compose up -d
```

### **Produção**
```bash
docker build -t sgu-app .
docker run -p 8080:8080 sgu-app
```

## 📁 **Estrutura do Projeto**

```
sgu/
├── src/main/java/
│   ├── application/          # Controllers e Use Cases
│   ├── domain/              # Entidades e regras de negócio
│   └── core/                # Configurações e segurança
├── src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   ├── application-prod.properties
│   └── db/migration/        # Scripts Flyway
├── docker-compose.yml
├── Dockerfile
├── env.example
└── README.md
```

## 🔄 **Migrações do Banco**

As migrações são executadas automaticamente via Flyway:
- `V1__Create_Departament.sql`
- `V2__Create_User.sql`
- `V3__Add_Telefone_DataCadastro_to_User.sql`
- `V4__Add_password_to_User.sql`
- `V5__Add_cpfCnpj_to_User.sql`
- `V6__Add_dataalteracao_to_User.sql`
- `V7__Create_ArquivoCSV.sql`

## 🚨 **Troubleshooting**

### **Problema: "Could not resolve placeholder"**
```bash
# Verificar se o arquivo .env existe
ls -la .env
```

### **Problema: "Connection refused"**
```bash
# Verificar se o PostgreSQL está rodando
docker ps | grep postgres
```

### **Problema: "JWT signature does not match"**
```bash
# Verificar JWT_SECRET no .env
echo $JWT_SECRET
```

## 📖 **Documentação Adicional**

- **`README-ENV.md`** - Configuração de variáveis de ambiente
- **`env.example`** - Exemplo de configuração
- **Swagger UI** - Documentação interativa da API

## 🤝 **Contribuição**

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 **Licença**

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

**🎉 Sistema SGU - Desenvolvido com as melhores práticas de desenvolvimento!**