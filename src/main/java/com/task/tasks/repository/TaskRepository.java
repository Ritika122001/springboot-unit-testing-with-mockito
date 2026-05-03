package com.task.tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.tasks.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
