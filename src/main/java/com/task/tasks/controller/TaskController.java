package com.task.tasks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.tasks.dto.TaskDTO;
import com.task.tasks.enumTask.TaskStatus;
import com.task.tasks.model.Task;
import com.task.tasks.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO task) {
        Task taskCreated = taskService.createTask(task);
        return ResponseEntity.status(201).body(taskCreated);
    }

    @GetMapping("/getTaskById/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            return ResponseEntity.status(200).body(task);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/deleteTaskById/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("id") Long id) {
        boolean exist = taskService.deleteTaskById(id);
        if (exist) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/updateTaskById/{id}")
    public ResponseEntity<Task> updateTaskById(@PathVariable("id") Long id, @RequestBody TaskDTO task) {
        Task updatedTask = taskService.updateTaskById(id, task);
        if (updatedTask != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PutMapping("/updateTaskByStatus/{id}")
    public ResponseEntity<Task> updatedTaskByStatus(@PathVariable("id") Long id, @RequestParam TaskStatus status) {
        Task updatedTask = taskService.updatedTaskByStatus(id, status);
        if (updatedTask != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
