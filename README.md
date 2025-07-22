🧥 STILEVO CLUB BACKEND
---
Um serviço robusto de backend desenvolvido para atender às operações de um e-commerce de roupas, com foco em escalabilidade, segurança e organização de código.
Este sistema gerencia funcionalidades essenciais como autenticação, controle de produtos, cadastros de usuários, pedidos e muito mais — estruturado seguindo boas práticas de design de software e utilizando a Layered Architecture (Controller → Service → Repository).
## 📁 Estrutura do Projeto

```
📦 root/
├── 📁 .idea/                        # Configurações do IntelliJ
├── 📁 .mvn/                         # Wrapper do Maven
│   └── 📁 wrapper/                 # Scripts do Maven Wrapper
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📁 com.stilevo.store.back.stilevo.project.api/
│   │   │       ├── 📁 config/              # Configurações da aplicação
│   │   │       │   └── 📁 security/        # Segurança (JWT, autenticação, etc.)
│   │   │       ├── 📁 controller/          # Controllers REST
│   │   │       ├── 📁 domain/              # Camada de domínio
│   │   │       │   ├── 📁 dto/             # Objetos de transferência de dados
│   │   │       │   │   ├── 📁 request/     # DTOs de requisição
│   │   │       │   │   └── 📁 response/    # DTOs de resposta
│   │   │       │   ├── 📁 entity/          # Entidades do modelo de dados
│   │   │       │   │   └── 📁 embeddable/  # Tipos embutidos (por exemplo, valores compostos)
│   │   │       │   ├── 📁 enums/           # Enumerações do sistema
│   │   │       │   └── 📁 repository/      # Interfaces do Spring Data JPA
│   │   │       ├── 📁 exception/           # Exceções customizadas
│   │   │       │   └── 📁 handler/         # Manipuladores de exceção global
│   │   │       ├── 📁 mapper/              # MapStruct mappers
│   │   │       └── 📁 service/             # Serviços e lógica de negócio
│   │   └── 📁 resources/
│   │       ├── 📁 static/                  # Arquivos estáticos (css, js, imagens)
│   │       └── 📁 templates/               # Templates Thymeleaf (ou outros)
│
│   └── 📁 test/
│       └── 📁 com.stilevo.store.back.stilevo.project.test/
│           ├── 📁 controllers/             # Testes dos controllers
│           ├── 📁 repositories/            # Testes dos repositórios
│           └── 📁 services/                # Testes dos serviços
```

## 🛠 Tecnologias Utilizadas

- Java 17 – Linguagem principal utilizada no desenvolvimento.

- Spring Boot – Framework para construção de aplicações Java de forma rápida e simplificada.

- Spring Web – Criação de APIs REST com suporte robusto a controllers, rotas e responses HTTP.

- Spring Security – Implementação de autenticação e autorização com proteção de endpoints.

- JWT (JSON Web Token) – Gerenciamento seguro de tokens de autenticação.

- JavaMailSender – Envio de e-mails (ex: confirmação de cadastro, recuperação de senha).

- JPA (Java Persistence API) – Abstração para manipulação de dados com bancos relacionais.

- Hibernate – Implementação ORM utilizada pelo JPA.

- Lombok – Redução de boilerplate com geração automática de getters, setters, constructors etc.

- JUnit – Ferramenta de testes unitários para garantir a qualidade do código.

- Mockito – Biblioteca de mocking para testes unitários e de integração.

## 🚀 Como Executar o Projeto
Siga os passos abaixo para clonar, configurar e rodar o backend do Stilevo Club localmente:

1. 🔄 Clone o repositório

```
bash

git clone https://github.com/seu-usuario/stilevo-club-backend.git
cd stilevo-club-backend
```

2. ☕ Configure o ambiente
Certifique-se de que você tenha instalado:

- Java 17+

- Maven 3.8+ ou use o wrapper (./mvnw)

- PostgreSQL (versão 13 ou superior)

Crie um banco de dados PostgreSQL chamado stilevo, ou configure o nome no application.properties ( caso deseje outro nome para seu Banco de Dados ).

🛠 Exemplo de configuração em application.properties:
```
properties

# Configurações do PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/stilevo
spring.datasource.username=seu_username
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT
api.security.token.secret=${JWT_SECRET:my-secret-key}

# Configurações para envio de e-mail com Gmail SMTP
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=username.exemplo@gmail.com
spring.mail.password=key_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

💡 Também é possível adaptar para outros bancos de dados relacionais como MySQL, bastando ajustar a URL e o hibernate.dialect.

3. ▶️ Execute a aplicação

Via terminal:

```
bash

./mvnw spring-boot:run
```

Ou rode diretamente a classe principal no seu IDE:
```
Application.java
```

A API estará acessível em:

```
http://localhost:8080
```

## 🚀 Utilidades da API
---

✅ Registro e login de usuários com autenticação via JWT.

✅ Criptografia da senha do usuário, ao realizar o registro.

✅ Filtragem de requisições pelos endpoints, determinando comportamentos pela Role do usuário – ADMIN ou USER – ou pela necessidade de autenticação.

✅ CRUD de todas as entidades da aplicação, com permissões definidas pela Role.

✅ Adição de produtos ao carrinho do cliente: adiciona caso não exista, ou incrementa a quantidade se já existir.

✅ Produtos no carrinho e nos pedidos: só é possível adicionar um produto aos pedidos se ele estiver previamente no carrinho.

✅ Envio automático de e-mails usando JavaMailSender.

✅ Arquitetura em camadas (Controller → Services → Repositories), separando as responsabilidades de cada camada.

✅ Uso de DTOs – Request e Response – para garantir uma transferência segura e controlada dos dados pela API.

✅ Queries otimizadas com a determinação ideal do carregamento das entidades, realziando queries personalizadas com Join Fetch ou tornando o carregamneto Lazy.

✅ Tratamento de exceções causadas por erros do cliente.

🔁 Exemplo: tentativa de cadastrar um usuário com e-mail já existente → Status: 409 (Conflict).

---

## 🔐 Autenticação e Autorização
A API utiliza autenticação baseada em JWT (JSON Web Token) para proteger endpoints e controlar acessos com base na Role do usuário (ADMIN ou USER).

### 🧾 Fluxo de Autenticação
O usuário faz login via /api/auth/login, enviando suas credenciais (e-mail e senha).

Se os dados estiverem corretos, o backend retorna um token JWT.

Esse token deve ser incluído no cabeçalho das requisições futuras para acessar rotas protegidas.

📥 Exemplo de Requisição de Login

```
POST /api/auth/login
Content-Type: application/json

{
  "email": "usuario@email.com",
  "senha": "123456"
}

```
📤 Resposta com o Token

```
{
  "user": {
      email: "usuario@email.com"
      username: usuario
      endereço: null
  }
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
### 🔑 Como Usar o Token

Adicione o token no header da requisição, quando o endpoint necessita de authenticação:

```
Authorization: Bearer {SEU_TOKEN_JWT}
```

### 🔍 Filtragem de Requisições com Spring Security

A aplicação usa um filtro de segurança que intercepta todas as requisições:

✅ Verifica se a rota exige autenticação.

🔐 Caso sim, procura pelo token JWT no cabeçalho Authorization.

🧠 Valida o token e extrai as informações do usuário (e-mail, role, id...).

🚫 Se o token for inválido, expirado ou inexistente, retorna erro 401 (Unauthorized).

✅ Após a validação, o contexto de segurança é preenchido, permitindo que os controllers identifiquem o usuário autenticado.

