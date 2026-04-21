# Task Time Tracker API

REST API для учёта рабочего времени сотрудников по задачам.

## Стек

- Java 21, Spring Boot 3.3.5
- MyBatis (доступ к БД)
- PostgreSQL 16
- Spring Security + JWT (jjwt 0.12.6)
- Bean Validation, SpringDoc OpenAPI (Swagger UI)
- JUnit 5, Mockito, Testcontainers

## Требования

- JDK 21+
- Maven 3.9+
- Docker (для базы данных и интеграционных тестов)

## Запуск

### 1. Поднять PostgreSQL

```bash
docker compose -f docker/docker-compose.yml up -d
```

### 2. Запустить приложение

```bash
mvn spring-boot:run
```

Приложение стартует на `http://localhost:8080`.

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Аутентификация

Все эндпоинты кроме `/auth/login` требуют JWT-токен.

Тестовые пользователи (пароль `password`):
- `alice`
- `bob`

### Получить токен

```http
POST /auth/login
Content-Type: application/json

{
  "username": "alice",
  "password": "password"
}
```

Ответ — строка с токеном. Передавать в заголовке:

```
Authorization: Bearer <token>
```

## Эндпоинты

| Метод  | URL                     | Описание                        |
|--------|-------------------------|---------------------------------|
| POST   | `/auth/login`           | Получить JWT-токен              |
| POST   | `/tasks`                | Создать задачу                  |
| GET    | `/tasks/{id}`           | Получить задачу по ID           |
| PATCH  | `/tasks/status`         | Изменить статус задачи          |
| POST   | `/time-records`         | Создать запись о времени        |
| POST   | `/time-records/by-period` | Получить записи за период     |

### Создать задачу

```http
POST /tasks
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Разработать API",
  "description": "Реализовать все эндпоинты"
}
```

### Изменить статус задачи

```http
PATCH /tasks/status
Authorization: Bearer <token>
Content-Type: application/json

{
  "taskId": "<uuid>",
  "status": "IN_PROGRESS"
}
```

Доступные статусы: `NEW`, `IN_PROGRESS`, `DONE`

### Создать запись о времени

```http
POST /time-records
Authorization: Bearer <token>
Content-Type: application/json

{
  "taskId": "<uuid>",
  "startTime": "2026-04-21T09:00:00Z",
  "endTime": "2026-04-21T11:30:00Z",
  "description": "Реализация сервисного слоя"
}
```

### Получить записи за период

```http
POST /time-records/by-period
Authorization: Bearer <token>
Content-Type: application/json

{
  "startTime": "2026-04-01T00:00:00Z",
  "endTime": "2026-04-30T23:59:59Z"
}
```

Возвращает только записи текущего авторизованного пользователя.

## Тесты

### Unit-тесты (без Docker)

```bash
mvn -Dtest='TaskServiceImplTest,TimeRecordServiceImplTest' test
```

### Интеграционные тесты (нужен Docker)

```bash
mvn -Dtest='RepositoryIntegrationTest' test
```

### Все тесты

```bash
mvn test
```
