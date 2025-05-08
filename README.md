# Real-Time Chat

## Descrição
O projeto **Real-Time Chat** é uma aplicação composta por dois serviços principais: **User Service** e **Chat Service**. Ele permite que usuários se registrem, façam login e participem de salas de bate-papo em tempo real utilizando WebSockets. A autenticação é baseada em JWT (JSON Web Token), garantindo segurança nas comunicações.

---

## Funcionalidades
- Registro de usuários com validação de dados.
- Login de usuários com geração de token JWT.
- Autenticação de usuários para acesso a recursos protegidos.
- Comunicação em tempo real via WebSockets.
- Envio de mensagens públicas e privadas.
- Gerenciamento de conexões WebSocket (entrada e saída de usuários).

---

## Serviços

### User Service
O **User Service** é responsável pela autenticação e gerenciamento de usuários. Ele fornece endpoints para registro, login e consulta de informações do usuário autenticado.

#### Endpoints
| Método | Endpoint       | Descrição                              |
|--------|----------------|----------------------------------------|
| POST   | `/register`    | Registra um novo usuário.              |
| POST   | `/login`       | Realiza login e retorna um token JWT.  |
| GET    | `/me`          | Retorna informações do usuário logado. |

#### Dependências
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- PostgreSQL Driver
- JJWT (JSON Web Token)

---

### Chat Service
O **Chat Service** é responsável pela comunicação em tempo real entre os usuários. Ele utiliza WebSockets para permitir o envio de mensagens públicas e privadas.

#### Endpoints
| Método | Endpoint       | Descrição                              |
|--------|----------------|----------------------------------------|
| WS     | `/ws-chat`     | Endpoint WebSocket para comunicação.   |
| STOMP  | `/app/chat.sendMessage` | Envia uma mensagem pública.   |
| STOMP  | `/app/chat.addUser`     | Adiciona um usuário à sala.    |
| STOMP  | `/app/chat.sendPrivateMessage` | Envia mensagem privada.|

#### Dependências
- Spring Boot Starter Web
- Spring Boot Starter WebSocket
- Spring Boot Starter JSON

---

## Como Executar

### Pré-requisitos
- Java 21 ou superior.
- Maven 3.9.9 ou superior.
- PostgreSQL configurado com as credenciais:
  - **Usuário:** `seuUsuario`
  - **Senha:** `suaSenha`
  - **Banco de dados:** `chat_db`

### Passos
1. Clone o repositório:
   ```bash
   git clone <link>
   cd Quick-Chat-using-WebSockets
   ```
2. Crie um arquivo `.env` na raíz do projeto como o arquivo `.env.exemple`.
3. Configure o banco de dados PostgreSQL:
- Crie o banco de dados chat_db.
- Certifique-se de que as credenciais no arquivo `.env` estão corretas.
4. Compile e execute os serviços:
- **User Service**:
   ```bash
   cd user-service
   ./mvnw spring-boot:run
   ```
- **Chat Service**:
   ```bash
   cd user-service
   ./mvnw spring-boot:run
   ```
5. Acesse os serviços:
- Chat Service: http://localhost:8080
- User Service: http://localhost:8081

## Estrutura do Projeto
```bash
real-time-chat/
├── .env
├── README.md
├── .vscode/
│   ├── launch.json
│   └── settings.json
├── chat-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/alex/chat_service/
│   │   │   ├── resources/
│   │   │   │   ├── application.properties
│   │   │   │   └── static/chat.html
│   │   └── test/
│   ├── pom.xml
│   └── .mvn/
├── user-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/alex/user_service/
│   │   │   ├── resources/
│   │   │   │   ├── application.properties
│   │   │   │   └── static/index.html
│   │   └── test/
│   ├── pom.xml
│   └── .mvn/
```

### Observações
O frontend está disponível nos arquivo HTML localizado na pasta `static` do serviço de chat.