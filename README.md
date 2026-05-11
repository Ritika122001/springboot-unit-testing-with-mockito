# Task Management API — Spring Boot

A RESTful Task Management application built with **Java** and **Spring Boot**, demonstrating clean service-layer architecture with comprehensive **unit testing** using **JUnit 5** and **Mockito**.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| ORM | Spring Data JPA |
| Testing | JUnit 5, Mockito |
| Build Tool | Maven |

---

## Project Structure

```
src/
├── main/java/com/task/tasks/
│   ├── model/
│   │   └── Task.java
│   ├── dto/
│   │   └── TaskDTO.java
│   ├── enumTask/
│   │   └── TaskStatus.java        # TODO, IN_PROGRESS, DONE
│   ├── repository/
│   │   └── TaskRepository.java
│   └── service/
│       └── TaskService.java
│
└── test/java/com/task/tasks/
    └── service/
        └── TaskServiceTest.java   # Unit tests (JUnit 5 + Mockito)
```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/tasks/createTask` | Create a new task |
| `GET` | `/tasks/getTaskById/{id}` | Get task by ID |
| `PUT` | `/tasks/updateTaskByStatus/status` | Update task status |
| `PUT` | `/tasks/updateTaskById/{id}` | Update task by ID |
| `DELETE` | `/tasks/deleteTaskById/{id}` | Delete task by ID |

---

## Task Status Enum

```java
public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE
}
```

---

## Unit Tests

This project focuses on **service-layer unit testing** using JUnit 5 and Mockito.
The repository layer is mocked — no database required to run tests.

### Test Coverage

| Test Method | Scenario | Assertion |
|---|---|---|
| `createTask()` | Happy path — creates and returns task | `assertNotNull`, `assertEquals` |
| `getTaskByIdFound()` | Task exists — returns correct task | `assertEquals` on all fields |
| `getTaskByIdNotFound()` | Task missing — throws RuntimeException | `assertThrows` + message check |
| `deleteTaskFindById()` | Task exists — deletes and returns true | `assertTrue`, `verify deleteById` |
| `deleteTaskNotFindById()` | Task missing — throws RuntimeException | `assertThrows`, `verify never deleteById` |
| `updatedTaskByStatusFound()` | Task exists — updates status correctly | `assertEquals(TaskStatus.DONE)` |
| `updatedTaskByStatusNotFound()` | Task missing — throws RuntimeException | `assertThrows`, `verify never save` |

### Testing Patterns Used

```java
// 1. Mock repository dependency
@Mock
private TaskRepository taskRepository;

// 2. Inject mocks into service
@InjectMocks
private TaskService taskService;

// 3. Stub repository behavior
Mockito.when(taskRepository.findById(id))
       .thenReturn(Optional.of(task));

// 4. Verify interactions
Mockito.verify(taskRepository).save(Mockito.any(Task.class));
Mockito.verify(taskRepository, Mockito.never()).deleteById(id);

// 5. Assert exception scenarios
RuntimeException ex = assertThrows(RuntimeException.class,
    () -> taskService.getTaskById(id));
assertEquals("Task not found", ex.getMessage());
```

---

## How to Run

### Clone the repository
```bash
git clone https://github.com/your-username/task-management-api.git
cd task-management-api
```

### Run the application
```bash
mvn spring-boot:run
```

### Run unit tests
```bash
mvn test
```

### Run tests with coverage report
```bash
mvn test jacoco:report
```
Coverage report generated at: `target/site/jacoco/index.html`

---

## Key Concepts Demonstrated

**Mockito `@Mock` vs `@InjectMocks`**
- `@Mock` creates a fake/stub of the dependency (TaskRepository)
- `@InjectMocks` creates the real service and injects the mocks into it

**Happy path vs edge case testing**
- Every service method has both a success test and a failure/not-found test
- Exception message is verified, not just exception type

**Verify over assert**
- `Mockito.verify()` confirms the correct repository method was called
- `Mockito.never()` confirms the repository was NOT called when it shouldn't be

---

## Sample Request

### Create Task
```json
POST /tasks
{
  "title": "Learn Spring Boot",
  "description": "Practice unit testing",
  "status": "TODO"
}
```

### Response
```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "description": "Practice unit testing",
  "status": "TODO"
}
```

---

## What I Learned

- Writing **isolated unit tests** without spinning up a real database
- Using **Mockito** to stub, spy, and verify repository interactions
- Testing **exception scenarios** with `assertThrows`
- Following the **Arrange → Act → Assert** pattern consistently
- Difference between **unit tests** (this project) and integration tests

---

## Author

**[Your Name]**
Java Full Stack Developer | Spring Boot | JUnit 5 | Mockito

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue)](https://linkedin.com/in/your-profile)
[![GitHub](https://img.shields.io/badge/GitHub-Follow-black)](https://github.com/your-username)
