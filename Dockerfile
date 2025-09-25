# Multi-stage build para otimizar tamanho da imagem
FROM maven:3.8.7-openjdk-17-slim AS build

WORKDIR /app

# Copiar arquivos de dependências primeiro (para cache do Maven)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Imagem de produção
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar JAR da aplicação
COPY --from=build /app/target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]