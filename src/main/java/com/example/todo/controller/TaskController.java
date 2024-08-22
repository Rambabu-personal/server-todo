package com.example.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.todo.pojo.Task;
import com.example.todo.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(value = "/services", produces = { "application/json" })
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        LOGGER.debug("TaskController.addTask() - with [{}]", task);
        try {
            Task createdTask = taskService.addTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            LOGGER.error("Error occurred while adding task", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks() {
        LOGGER.debug("TaskController.getAllTasks()");
        try {
            List<Task> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving tasks", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        LOGGER.debug("TaskController.getTaskById() - with ID [{}]", id);
        try {
            Task task = taskService.getTaskById(id);
            if (task != null) {
                return ResponseEntity.ok(task);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving task", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        LOGGER.debug("TaskController.updateTask() - with ID [{}] and task [{}]", id, task);
        try {
            Task updatedTask = taskService.updateTask(id, task);
            if (updatedTask != null) {
                return ResponseEntity.ok(updatedTask); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while updating task", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTask(@PathVariable("id") Long id) {
        LOGGER.debug("TaskController.deleteTask() - with ID [{}]", id);
        try {
            boolean isDeleted = taskService.deleteTask(id);
            if (isDeleted) {
                return ResponseEntity.ok(isDeleted);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while deleting task", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
