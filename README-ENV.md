# 🔧 Configuração de Variáveis de Ambiente - SGU

Guia rápido para configurar as variáveis de ambiente do projeto SGU.

## ⚡ **Configuração Rápida**

### **1. Copiar arquivo de exemplo**
```bash
cp env.example .env
```

### **2. Editar configurações**
```bash
# Editar o arquivo .env com suas configurações
notepad .env  # Windows
nano .env     # Linux/Mac
```

### **3. Executar aplicação**
```bash
# Com Docker (recomendado)
docker-compose up -d

# Ou com Maven
mvn spring-boot:run
```

## 📋 **Variáveis Principais**

### **Banco de Dados**
```bash
DB_URL=jdbc:postgresql://localhost:5432/dbsgu
DB_USERNAME=postgres
DB_PASSWORD=sua_senha_segura
```

### **Segurança JWT**
```bash
JWT_SECRET=sua_chave_jwt_muito_segura_com_pelo_menos_32_caracteres
JWT_EXPIRATION=86400000  # 24 horas
```

### **CORS**
```bash
CORS_ORIGINS=http://localhost:3000,http://localhost:8080
```

### **Logs**
```bash
LOG_LEVEL=INFO
SHOW_SQL=false
```

## 🔧 **Configurações por Ambiente**

### **Desenvolvimento**
```bash
SPRING_PROFILES_ACTIVE=dev
LOG_LEVEL=DEBUG
SHOW_SQL=true
```

### **Produção**
```bash
SPRING_PROFILES_ACTIVE=prod
LOG_LEVEL=INFO
SHOW_SQL=false
```

## 🚨 **Troubleshooting**

### **"Could not resolve placeholder"**
- Verifique se o arquivo `.env` existe
- Confirme se todas as variáveis estão definidas

### **"Connection refused"**
- Verifique se o PostgreSQL está rodando
- Confirme as credenciais do banco

### **"JWT signature does not match"**
- Verifique se o `JWT_SECRET` está correto
- Confirme se é o mesmo usado para gerar tokens

## ✅ **Verificação**

1. **Health Check**: http://localhost:8080/api/health
2. **Swagger**: http://localhost:8080/swagger-ui.html
3. **Logs**: Verifique os logs da aplicação

---

**🎯 Use o arquivo `env.example` como template!**
