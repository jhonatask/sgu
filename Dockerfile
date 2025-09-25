# Multi-stage build para otimizar tamanho da imagem
FROM maven:3.9.4-openjdk-21-slim AS build

WORKDIR /app

# Copiar arquivos de dependências primeiro (para cache do Maven)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Imagem de produção
FROM openjdk:21-jre-slim

WORKDIR /app

# Criar usuário não-root para segurança
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Instalar dependências necessárias
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copiar JAR da aplicação
COPY --from=build /app/target/*.jar app.jar

# Criar diretórios de log
RUN mkdir -p /app/logs && chown -R appuser:appuser /app

# Mudar para usuário não-root
USER appuser

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/api/health || exit 1

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
