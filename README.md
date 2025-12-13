# Task Manager Backend

# Task Manager Backend

Small Spring Boot backend for a task management app. This README summarizes available features, REST APIs, data model, how to run, and how to extend the project.

## Quick facts

- Base URL: `http://localhost:8081/api` (configured in `src/main/resources/application.properties`)
- Java: 21
- Framework: Spring Boot (embedded Tomcat)
- Persistence: Spring Data JPA (Postgres configured in `application.properties`)
- CORS: configured to allow `http://localhost:5173` for local frontend development

## How to run

From project root (`tasks` folder):

```powershell
cd C:/Users/seher/tasks/Task-Manager-Backend/tasks
.\mvnw.cmd spring-boot:run
```

Build (skip tests):

```powershell
.\mvnw.cmd -DskipTests package
```

DB: Postgres configured at `jdbc:postgresql://localhost:5432/tasksdb` in `application.properties`.

## Error handling

- `IllegalArgumentException` thrown in controllers/services is handled by `GlobalExceptionHandler`, which returns HTTP 400 with JSON `ErrorResponse`.

## Controllers & APIs

All endpoints are mounted under `/api` (see `server.servlet.context-path=/api`). Controllers return/accept JSON. Key controllers:

- `TaskListController` (`/api/task-lists`)

  - GET `/task-lists` — list all task lists
  - POST `/task-lists` — create task list (body: `TaskListDto`)
  - GET `/task-lists/{id}` — get by id
  - PUT `/task-lists/{id}` — update
  - DELETE `/task-lists/{id}` — delete

- `TaskController` (`/api/task-lists/{task_list_id}/tasks`)

  - GET — list tasks in a task list
  - POST — create task (body: `TaskDto`) — `title` required

- `UserController` (`/api/users`)

  - CRUD for users (use `UserDto`)

- `RoleController` (`/api/roles`)

  - CRUD for roles (use `RoleDto`)

- `WorkspaceController` (`/api/workspaces`)

  - CRUD for workspaces (resolve `ownerId` via `UserRepository` when provided)

- `ProjectController` (`/api/projects`)

  - CRUD for projects (resolve `workspaceId` and `ownerId` via repos)

- `SubtaskController` (`/api/subtasks`)

  - CRUD for subtasks (resolve `parentTaskId` via `TaskRepository`)

- `CommentController` (`/api/comments`)
  - CRUD for comments (resolve `taskId` and `authorId` via repos)

DTO shapes (examples):

- `TaskDto`: `{ id, title, description, dateDue, priority, status }`
- `TaskListDto`: `{ id, title, description, taskCount, progress, tasks }`
- `UserDto`: `{ id, username, email, displayName, active, created, lastLogin }`
- `ProjectDto`, `WorkspaceDto`, `SubtaskDto`, `CommentDto` exist under `src/main/java/com/project/tasks/domain/dto`.

## Data model (new entities)

Added entities under `src/main/java/com/project/tasks/domain/entities`:

- `User` — account, owner/author reference
- `Role` — simple role record
- `Workspace` — grouping of projects (owner is a `User`)
- `Project` — belongs to `Workspace`; has owner
- `Subtask` — child of `Task`
- `Comment` — attached to `Task`, authored by `User`

Existing entities: `Task`, `TaskList`, `TaskPriority`, `TaskStatus` remain primary domain model.

Relations: entities mostly use `@ManyToOne(fetch = FetchType.LAZY)` with foreign keys. DTOs carry id references for relations (e.g., `ownerId`).

## Mappers

Manual mapper interfaces and implementations were added in `src/main/java/com/project/tasks/mappers` and `mappers/impl`.

- Purpose: convert DTO ↔ Entity (parse dates, map enums, create placeholder entities from ids).
- Pattern: controllers use mappers + repositories to convert incoming DTOs and ensure related entities exist before saving.

Examples: `TaskMapperImpl`, `UserMapperImpl`, `ProjectMapperImpl`.

## Repositories

Spring Data JPA repositories under `src/main/java/com/project/tasks/repositories` provide CRUD for all entities (e.g., `UserRepository`, `ProjectRepository`). They extend `JpaRepository<Entity, UUID>`.

## Validation & error responses

- Basic field checks performed in controllers (e.g., required `title` checks). These throw `IllegalArgumentException` and return 400.
- Recommendation: add Jakarta Validation (`@NotBlank`, `@NotNull`) on DTO fields and use `@Valid` in controllers to standardize validation.

## Extending the project

- Add a service layer (`@Service`) to encapsulate business logic and transactions.
- Replace manual mappers with MapStruct for less boilerplate (optional).
- Add controllers for other entities or expand existing controllers (e.g., route `POST /api/task-lists/{id}/tasks/{taskId}/subtasks`).
- Add OpenAPI/Swagger (springdoc) to auto-generate API docs.

## Example cURL

Create a task list:

```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"title":"Groceries","description":"Weekly list"}' \
  http://localhost:8081/api/task-lists
```

Create a project:

```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"Website","workspaceId":"<workspace-uuid>"}' \
  http://localhost:8081/api/projects
```
