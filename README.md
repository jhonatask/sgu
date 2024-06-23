# Getting Started

### Reference Documentation

Documentação do Projeto<br><br>
Visão Geral<br><br>
Este projeto é um sistema de gerenciamento de usuários desenvolvido utilizando Spring Boot. O sistema oferece funcionalidades de CRUD para usuários, com endpoints para criação, leitura, atualização e exclusão de usuários.

### Arquitetura do projeto e tecnologias utilizadas

O sistema segue a arquitetura de camadas DDD, com os seguintes componentes principais:

Application: Contém os controladores REST e os modelos de API (DTOs).<br>
Domain: Contém as entidades, servicesn mapper e repositórios do domínio.<br>
Core: configuracao de segurança, exceptions e configurações gerais.


### Tecnologias Utilizadas

Spring Boot: Framework principal para desenvolvimento da aplicação.<br>
Spring Data JPA: Abstração para acesso a dados.<br>
Hibernate: Implementação de JPA.<br>
PostgreSQL 16: Banco de dados relacional.<br>
Flyway: Ferramenta para versionamento e migrações do banco de dados.<br>
JUnit: Framework de testes.<br>
MockMvc: Utilizado para testes de integração dos endpoints.<br>
OpenAPI: Ferramenta para documentação da API.<br>
Maven: Ferramenta de gerenciamento de dependências e construção do projeto.<br>

### Configuração do Projeto
Requisitos:<br>
JDK 21  <br>
Maven 3.6.3 ou superior <br>
PostgreSQL 16 <br>

### Documentação da API
A documentação da API é gerada automaticamente utilizando OpenAPI. <br>
Para acessar a documentação da API, abra o navegador e vá para <br><br>
``http://localhost:8080/swagger-ui.html.``

Utilize o endpoint de criação de usuario passando o password no json para criar usuario administrador e poder logar no sistema.

#### Clone o Repositório
``git clone https://github.com/jhonatask/sgu.git
``<br><br>
Importe o projeto na ide de preferencia ( Recomendo Intellij IDEA)
#### Configurar Banco de Dados, use algum ferramenta sgbd para criar database
``CREATE DATABASE dbsgu;
`` <br><br>
Atualize o arquivo application.properties  com as informações do seu banco de dados PostgreSQL: <br>

spring.datasource.url=jdbc:postgresql://localhost:5432/dbsgu<br>
spring.datasource.username=seu-usuario<br>
spring.datasource.password=sua-senha<br>
