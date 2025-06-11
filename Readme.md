# API de Gerenciamento de Biblioteca

![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)
![Maven](https://img.shields.io/badge/Maven-3.x-orange.svg)
![H2 Database](https://img.shields.io/badge/H2%20Database-lightgray.svg)
![Postgresql](https://img.shields.io/badge/postgresql-logo.svg?style=flat&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black.svg?style=flat&logo=json-web-tokens)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D.svg?style=flat&logo=swagger&logoColor=black)

Uma API RESTful completa para gerenciar uma biblioteca, permitindo o cadastro de autores, livros, usuários e o controle de empréstimos.

---

## 🚀 Funcionalidades

* **Autores:** Cadastro, listagem, busca por ID, atualização e remoção.
* **Livros:** Cadastro, listagem, busca por ID, atualização e remoção, além de controle de status (disponível/emprestado).
* **Usuários:** Cadastro, listagem, busca por ID, atualização e remoção.
* **Empréstimos:** Registro de empréstimos e devoluções.
* **Autenticação JWT:** Registro de novos usuários e login para obter um token de acesso para acessar endpoints protegidos.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.x
* **Gerenciador de Dependências:** Maven
* **Banco de Dados:**
    * H2 Database (para desenvolvimento e testes em memória)
    * Postgresql (para ambiente de produção/desenvolvimento local persistente)
* **Persistência:** Spring Data JPA / Hibernate
* **Segurança:** Spring Security com JWT (JSON Web Tokens)
* **Documentação da API:** Swagger/OpenAPI (ainda a ser configurado)
* **Validação:** Bean Validation
* **Mapeamento de Objetos:** ModelMapper (ou DTOs manuais)

---

## ⚙️ Configuração do Projeto

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

* **Java 21 (JDK):** [Download](https://www.oracle.com/java/technologies/downloads/)
* **Maven 3.x:** [Download](https://maven.apache.org/download.cgi)
* **Git:** [Download](https://git-scm.com/downloads)
* **Postman (ou ferramenta similar):** Para testar os endpoints da API.

### 1. Clonar o Repositório

```bash
git clone https://github.com/ppablolds/API-Sistema-de-Gestao-de-Biblioteca.git
cd API-Sistema-de-Gestao-de-Biblioteca/src
```

### 2. Configuração do Banco de Dados

A aplicação utiliza um banco de dados em memória H2 por padrão para facilitar o desenvolvimento. Nenhuma configuração adicional é necessária para começar.

Para configurar o MySQL (opcional):

Crie um banco de dados chamado biblioteca_db (ou o nome que preferir).

Atualize o arquivo src/main/resources/application.properties com suas credenciais do MySQL:

```application.properties
# PostgreSQL Database Configuration

# URL de conexão com o banco de dados PostgreSQL
# Substitua 'localhost' pelo IP do seu servidor PostgreSQL, '5432' pela porta
# e 'biblioteca_db' pelo nome do seu banco de dados.
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca_db

# Nome de usuário para acessar o banco de dados
spring.datasource.username=seu_usuario_postgres

# Senha para acessar o banco de dados
spring.datasource.password=sua_senha_postgres

# Driver de conexão para PostgreSQL
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuração do JPA/Hibernate para PostgreSQL
# update: Tenta atualizar o schema do banco de dados para corresponder às entidades.
# create-drop: Cria o schema no início e o dropa no final da sessão (bom para testes).
# create: Cria o schema no início (bom para o primeiro deploy, mas apaga dados existentes).
# none: Não faz nada com o schema (você gerencia as DDLs manualmente).
spring.jpa.hibernate.ddl-auto=update

# Mostra as queries SQL geradas pelo Hibernate no console
spring.jpa.show-sql=true

# Configuração do dialeto do Hibernate para PostgreSQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Opcional: Se você estiver tendo problemas com o pool de conexões e for usar HikariCP (padrão do Spring Boot)
# spring.datasource.hikari.maximum-pool-size=10
# spring.datasource.hikari.minimum-idle=5
# spring.datasource.hikari.connection-timeout=30000
# spring.datasource.hikari.idle-timeout=600000
# spring.datasource.hikari.max-lifetime=1800000
```

### 3. Configuração JWT

No arquivo src/main/resources/application.properties, configure sua chave secreta e tempo de expiração para os tokens JWT:

```application.properties
# JWT Configuration
jwt.secret=SUA_CHAVE_SECRETA_MUITO_LONGA_E_SEGURA_AQUI_MINIMO_32_BYTES
jwt.expiration=3600000 # 1 hora em milissegundos
```

### 4. Rodar a Aplicação

**Com Maven:**
```bash
mvn spring-boot:run
```

A aplicação estará disponível em http://localhost:8080 (ou a porta configurada).

---

## 📋 Endpoints da API

A API segue o padrão RESTful. A seguir, alguns exemplos dos endpoints disponíveis (ou a serem implementados).

(Importante: A documentação completa da API estará disponível via Swagger/OpenAPI após a configuração. Para isso, rode a aplicação e acesse http://localhost:8080/swagger-ui.html ou http://localhost:8080/swagger-ui/index.html.)

**Autenticação e Registro ``(/auth)``**

 - POST /auth/register: Registra um novo usuário.

**Body (JSON):** 
````json
{
  "username": "novoUser",
  "password": "senhaForte",
  "username": "username@example.com"
}
````
**Resposta:** ``201 Created`` com os dados do usuário.

**POST /auth/login:** Realiza o login e retorna um token JWT.

**Body (JSON):** 
````json
{
  "username": "novoUser",
  "password": "senhaForte"
}
````
**Resposta:** ``200 OK`` com ``{"jwt": "seu.token.jwt.aqui"}``

---

**Autores (/autores)**

(Para testar estes endpoints, inclua o cabeçalho Authorization: Bearer SEU_TOKEN_JWT em suas requisições após o login.)

**POST /autores:** Cria um novo autor.

**Body (JSON):**
````json
{
"nome": "Nome do Autor"
}
````
**Resposta:** ``201 Created`` com os dados do autor criado.

**GET /autores:** Lista todos os autores.

**Resposta:** ``200 OK`` com uma lista de autores.

**GET /autores/{id}:** Busca um autor por ID.

**Resposta:** ``200 OK`` com os dados do autor ou ``404 Not Found``.

**PUT /autores/{id}:** Atualiza um autor existente.

**Body (JSON):**
````json
{
  "nome": "Novo Nome do Autor"
}
````

Resposta: ``200 OK`` com os dados do autor atualizado.

**DELETE /autores/{id}:** Deleta um autor por ID.

**Resposta:** ``204 No Content`` ou ``404 Not Found``.

---

## 🤝 Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

---

## ✉️ Contato
Pablo Silva - ls8pablo@gmail.com - [LinkedIn](https://linkedin.com/in/ppablolds)

---

## 📝 Licença
Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE.md) para detalhes.