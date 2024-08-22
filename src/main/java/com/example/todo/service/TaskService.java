package com.example.todo.service;

import java.rmi.ServerException;
import java.util.List;

import com.example.todo.pojo.Task;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TaskService {
	public Task addTask(Task task) throws ServerException, JsonProcessingException;
	public Task getTaskById(Long id) throws ServerException;
	public List<Task> getAllTasks() throws ServerException;
	public Task updateTask(Long id, Task task) throws ServerException;
	public boolean deleteTask(Long id) throws ServerException;
}
