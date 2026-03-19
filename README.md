# 💉 GLIC — Gestão de Insulina e Controle Glicêmico

API REST para gerenciamento de insulina e controle glicêmico, voltada para pessoas com diabetes. Permite cadastro de insulinas, registro de aplicações, cálculo de dose sugerida com base na contagem de carboidratos e acompanhamento do histórico de uso.

## Tecnologias

- **Java 21** + **Spring Boot 4.0.3**
- **Spring Security** + **JWT** (autenticação stateless)
- **Spring Data JPA** + **PostgreSQL**
- **Bean Validation** (jakarta.validation)
- **Docker** + **Docker Compose**
- **Maven**

## Funcionalidades

- Cadastro e autenticação de usuários com JWT
- CRUD de insulinas (canetas e frascos, tipos rápida/lenta/intermediária)
- Registro de aplicações de insulina com data/hora e unidades aplicadas
- Cálculo automático de dose sugerida baseado na contagem de carboidratos
- Perfil do usuário com tipo de diabetes, data de diagnóstico e idade calculada
- Filtro de registros de aplicação por data
- Verificação de propriedade (cada usuário acessa apenas seus dados)

## Como rodar

### Com Docker (recomendado)

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/Glic.git
cd Glic
```

2. Crie o arquivo `.env` na raiz do projeto:
```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=sua_senha
DB_USER=postgres
DB_PASS=sua_senha
JWT_SECRET=sua-chave-secreta-com-no-minimo-32-caracteres
```

3. Suba os containers:
```bash
docker-compose up --build
```

A API estará disponível em `http://localhost:8080`.

### Sem Docker (local)

**Pré-requisitos:** Java 21, Maven, PostgreSQL rodando na porta 5432.

1. Crie o banco de dados `Glic` no PostgreSQL.

2. Configure o arquivo `.env` na raiz:
```env
DB_URL=jdbc:postgresql://localhost:5432/Glic
DB_USER=postgres
DB_PASS=sua_senha
```

3. Rode a aplicação:
```bash
./mvnw spring-boot:run
```

## Endpoints

### Autenticação
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/auth/register` | Cadastrar usuário |
| POST | `/auth/login` | Login (retorna JWT) |

### Usuários (requer autenticação)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/users/me` | Dados do usuário logado |
| PUT | `/users/me` | Atualizar dados |
| PUT | `/users/me/profile` | Atualizar perfil clínico |
| DELETE | `/users/me` | Excluir conta |

### Insulinas (requer autenticação)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/insulins` | Listar insulinas |
| GET | `/insulins/{id}` | Buscar por ID |
| POST | `/insulins` | Cadastrar insulina |
| PUT | `/insulins/{id}` | Atualizar insulina |
| DELETE | `/insulins/{id}` | Excluir insulina |

### Registros de aplicação (requer autenticação)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/logs` | Listar todos os registros |
| GET | `/logs?date=2026-03-19` | Filtrar por data |
| GET | `/logs/{id}` | Buscar por ID |
| POST | `/logs/insulina/{insulinId}` | Registrar aplicação |
| PUT | `/logs/{id}` | Atualizar registro |
| DELETE | `/logs/{id}` | Excluir registro |

## Exemplo de uso

**Registrar:**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name": "Pedro", "email": "pedro@email.com", "password": "123456"}'
```

**Login:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "pedro@email.com", "password": "123456"}'
```

**Cadastrar insulina** (usar o token retornado no login):
```bash
curl -X POST http://localhost:8080/insulins \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{
    "name": "Novorapid",
    "units": 300,
    "price": 45.90,
    "quantity": 1,
    "purchaseDate": "2026-03-19",
    "type": "Rapid",
    "format": "Pens"
  }'
```

## Estrutura do projeto

```
src/main/java/com/pedro/glic/
├── controller/          # Endpoints REST
├── dto/                 # Data Transfer Objects (records)
├── entity/              # Entidades JPA
├── enums/               # DiabetesType, InsulinType, InsulinFormat
├── repository/          # Spring Data JPA
├── security/            # JWT, filtros, configuração Spring Security
└── service/             # Regras de negócio
```

## Docker

O projeto utiliza **multi-stage build** para gerar uma imagem otimizada:

- **Estágio 1 (build):** Compila o projeto com JDK 21
- **Estágio 2 (runtime):** Roda apenas o .jar com JRE 21

O `docker-compose.yml` orquestra dois serviços:
- `api` — aplicação Spring Boot na porta 8080
- `db` — PostgreSQL 16 com volume persistente

## Autor

**Pedro Henrique Carvalho Pereira**

- [GitHub](https://github.com/pedroccarv)
- [LinkedIn](https://linkedin.com/in/pedrohcpereira)