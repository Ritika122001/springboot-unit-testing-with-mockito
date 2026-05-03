package com.task.tasks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.task.tasks.dto.TaskDTO;
import com.task.tasks.enumTask.TaskStatus;
import com.task.tasks.model.Task;
import com.task.tasks.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask() {
        TaskDTO dto = new TaskDTO();
        dto.setTitle("Learn Spring Boot");
        dto.setDescription("Practice unit testing");
        dto.setStatus(TaskStatus.TODO);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Learn Spring Boot");
        savedTask.setDescription("Practice unit testing");
        savedTask.setStatus(TaskStatus.TODO);

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(dto);

        assertNotNull(result);
        assertEquals(savedTask, result);
        assertEquals("Learn Spring Boot", result.getTitle());
        assertEquals(TaskStatus.TODO, result.getStatus());
        assertEquals("Practice unit testing", result.getDescription());

        Mockito.verify(taskRepository).save(Mockito.any(Task.class));
    }

    @Test
    public void getTaskByIdFound() {
        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setDescription("Test Task");
        savedTask.setTitle("Task Title");
        savedTask.setStatus(TaskStatus.DONE);

        Long id = savedTask.getId();
        Mockito.when(taskRepository.findById(savedTask.getId())).thenReturn(Optional.of(savedTask));

        Task result = taskService.getTaskById(id);
        assertNotNull(result);
        assertEquals(savedTask, result);
        assertEquals("Test Task", result.getDescription());
        assertEquals("Task Title", result.getTitle());
        assertEquals(TaskStatus.DONE, result.getStatus());

        Mockito.verify(taskRepository).findById(id);
    }

    @Test
    public void getTaskByIdNotFound() {
        Long id = 1L;
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.getTaskById(id));
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    public void deleteTaskFindById() {
        Long id = 1L;
        Task existingTask = new Task();
        existingTask.setId(id);
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        boolean result = taskService.deleteTaskById(id);

        Mockito.verify(taskRepository).deleteById(id);

        assertTrue(result);
    }

    @Test
    public void deleteTaskNotFindById() {

        Long id = 1L;
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());
        // boolean result = taskService.deleteTaskById(id);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.deleteTaskById(id));
        assertEquals("Task not found", exception.getMessage());

        Mockito.verify(taskRepository, Mockito.never()).deleteById(id);
    }

    @Test
    public void updatedTaskByStatusFound() {
        Long id = 1L;
        Task exisitingTask = new Task();
        exisitingTask.setId(id);
        exisitingTask.setStatus(TaskStatus.TODO);
        exisitingTask.setDescription("Test Task");
        exisitingTask.setTitle("Task Title");

        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(exisitingTask));

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(exisitingTask);

        Task result = taskService.updatedTaskByStatus(id, TaskStatus.DONE);

        assertNotNull(result);
        assertEquals(TaskStatus.TODO, result.getStatus());
        assertEquals("Test Task", result.getDescription());
        assertEquals("Task Title", result.getTitle());

        Mockito.verify(taskRepository).save(Mockito.any(Task.class));
    }

    @Test
    public void updatedTaskByStatusNotFound() {
        Long id = 1L;

        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.updatedTaskByStatus(id, TaskStatus.DONE));

        assertEquals("Task not found", exception.getMessage());

        Mockito.verify(taskRepository, Mockito.never()).save(Mockito.any(Task.class));
    }

}
