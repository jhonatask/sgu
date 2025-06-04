# Getting Started

### Reference Documentation

Documentação do Projeto<br><br>
Visão Geral<br><br>
Este projeto é um sistema de gerenciamento de usuários desenvolvido utilizando Spring Boot. O sistema oferece funcionalidades de CRUD para usuários, com endpoints para criação usuario unico ou em massa por arquivo csv, leitura, atualização e exclusão de usuários.

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
PostgreSQL ultima versao <br>

### Documentação da API
A documentação da API é gerada automaticamente utilizando OpenAPI. <br>
Para acessar a documentação da API, abra o navegador e vá para <br><br>
``http://localhost:8080/swagger-ui.html.``

#### Clone o Repositório
``git clone https://github.com/jhonatask/sgu.git
``<br><br>
Importe o projeto na ide de preferencia ( Recomendo Intellij IDEA)
#### Configurar Banco de Dados, use algum ferramenta sgbd para criar database
### Crie um arquivo docker-compose para ser executado ou crie o banco de dados manualmente no PostgreSQL, caso queira usar docker-compose, crie um arquivo docker-compose.yml com o seguinte conteudo:

``docker-compose up``<br>

``CREATE DATABASE dbsgu;
`` <br><br>

#### Query para inserir o caminho do diretório CSV no banco de dados
``INSERT INTO diretorio_csv (caminho) VALUES ('C:/dados/clientes_csv');
``<br><br>

Atualize o arquivo application.properties  com as informações do seu banco de dados PostgreSQL: <br>


### Liberei o acesso ao swagger para que possa criar o departamento e o usuario pelo swagger.
### Tambem e possivel pegar o token para acessar os demais endpoints, colocando valor do token no header Authorization Bearer token pelo swagger, postman ou insominia.
SQL insert usuario master (senha: Pamonha123*)<br>
``INSERT INTO public.users
("name", email, department_id, telefone, "password", cpforcnpj, dataalteracao)
VALUES( 'nome', 'email', 'id do departamento criado'::uuid, 'telefone', '$2a$10$P5CkkDv41fmmoPwP3btzP.0bqBe3a3SPCkFJOGPMs9egCdTKM1xUy', 'cpf', NULL);``

spring.datasource.url=jdbc:postgresql://localhost:5432/dbsgu<br>
spring.datasource.username=seu-usuario<br>
spring.datasource.password=sua-senha<br>

### subistituir o valor do diretorio_csv no banco de dados, com o caminho do diretório onde os arquivos CSV serão armazenados. <br>
csv.diretorio.id=c75d04ee-282a-4567-a92e-3bdc8af0d355

As demais requisicoes tanto pelo swagger, quanto por postman ou insominia e pelo front so tem acesso apos o login.
