# SafeZone API

API de Monitoramento de Áreas de Risco com JWT

## Descrição

A SafeZone API é uma aplicação Spring Boot que fornece endpoints para monitoramento de áreas de risco, gerenciamento de sensores e alertas. A API utiliza autenticação JWT para garantir a segurança dos dados.

## Integrantes
RM556270 - Bianca Vitoria - 2TDSPZ
RM555166 - Guilherme Camargo - 2TDSPM
RM555131 - Icaro Americo - 2TDSPM

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.0
- Spring Security
- Spring Data JPA
- Oracle Database
- JWT (JSON Web Token)
- Swagger/OpenAPI 3
- Maven
- Lombok

## Estrutura do Projeto

```
src/main/java/com/safezone/
├── config/          # Configurações do Spring
├── controller/      # Controladores REST
├── dto/            # Objetos de Transferência de Dados
├── model/          # Entidades JPA
├── repository/     # Repositórios JPA
├── service/        # Lógica de Negócio
└── util/           # Utilitários
```

## Endpoints da API

### Autenticação (AuthController)

- **POST /auth/register**
  - Registra um novo usuário
  - Body: `{ "nome": "string", "email": "string", "senha": "string" }`

- **POST /auth/login**
  - Autentica um usuário
  - Body: `{ "email": "string", "senha": "string" }`
  - Retorna: Token JWT

### Locais de Risco (LocalDeRiscoController)

- **GET /locais**
  - Lista locais de risco
  - Parâmetros: cidade, bairro (opcionais)
  - Retorna: Lista paginada

- **GET /locais/{id}**
  - Busca local por ID
  - Retorna: Dados do local

- **POST /locais**
  - Cria novo local
  - Body: `{ "cidade": "string", "bairro": "string", "rua": "string", "numero": "string" }`

- **PUT /locais/{id}**
  - Atualiza local existente
  - Body: Mesmo formato do POST

- **DELETE /locais/{id}**
  - Remove local

### Alertas (AlertaController)

- **GET /alertas**
  - Lista alertas
  - Parâmetros: sensorId, status (opcionais)
  - Retorna: Lista paginada

- **GET /alertas/{id}**
  - Busca alerta por ID
  - Retorna: Dados do alerta

- **PUT /alertas/{id}/status**
  - Atualiza status do alerta
  - Parâmetros: status (PENDENTE, EM_ANALISE, RESOLVIDO, IGNORADO)

- **DELETE /alertas/{id}**
  - Remove alerta

### Sensores (SensorController)

- **GET /sensores**
  - Lista sensores
  - Retorna: Lista paginada

- **GET /sensores/{id}**
  - Busca sensor por ID
  - Retorna: Dados do sensor

- **POST /sensores**
  - Cria novo sensor
  - Body: `{ "nome": "string", "descricao": "string" }`

- **PUT /sensores/{id}**
  - Atualiza sensor existente
  - Body: Mesmo formato do POST

- **DELETE /sensores/{id}**
  - Remove sensor

## Configuração do Banco de Dados

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Segurança

- Autenticação via JWT
- Tokens com expiração de 24 horas
- Senhas criptografadas com BCrypt
- Endpoints protegidos por roles

## Documentação da API

A documentação completa da API está disponível via Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

## Executando o Projeto

1. Clone o repositório
2. Configure o banco de dados Oracle
3. Execute o projeto:
```bash
./mvnw spring-boot:run
```

## Testes

Para executar os testes:
```bash
./mvnw test
```
