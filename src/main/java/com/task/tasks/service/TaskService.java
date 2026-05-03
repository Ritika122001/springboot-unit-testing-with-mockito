package com.task.tasks.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.task.tasks.dto.TaskDTO;
import com.task.tasks.enumTask.TaskStatus;
import com.task.tasks.model.Task;
import com.task.tasks.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskDTO task) {
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setTitle(task.getTitle());
        newTask.setStatus(task.getStatus());
        return taskRepository.save(newTask);
    }

    public Task getTaskById(@NonNull Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        return task;
    }

    public boolean deleteTaskById(@NonNull Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        if (task != null) {
            taskRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Task updateTaskById(@NonNull Long id, TaskDTO task) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setDescription(task.getDescription());
        existingTask.setTitle(task.getTitle());
        existingTask.setStatus(task.getStatus());
        return taskRepository.save(existingTask);
    }

    public Task updatedTaskByStatus(@NonNull Long id, TaskStatus status) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setStatus(status);
        return taskRepository.save(existingTask);

    }

}
