package com.example.todo.service;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.dao.GenaralDao;
import com.example.todo.pojo.Task;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class TaskServiceImpl implements TaskService {
	final static Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private GenaralDao genaralDao;

	@Override
	public Task addTask(Task task) throws ServerException, JsonProcessingException {

		LOGGER.info("TaskServiceImpl.addTask() -  [" + task + "]");

		Task t = new Task();

		t.setTitle(task.getTitle());
		t.setDescription(task.getDescription());
		t.setDueDate(task.getDueDate());
		return genaralDao.save(t);

	}

	@Override
	public Task getTaskById(Long id) throws ServerException {
		LOGGER.info("TaskServiceImpl.getTaskById() - ID [{}]", id);

		try {
			Optional<Task> task = genaralDao.findById(id);
			return task.orElse(null);
		} catch (Exception e) {
			LOGGER.error("Error occurred while retrieving task", e);
			throw new ServerException("Error occurred while retrieving task", e);
		}
	}

	@Override
	public List<Task> getAllTasks() throws ServerException {
		LOGGER.info("TaskServiceImpl.getAllTasks()");

		try {
			return genaralDao.findAll();
		} catch (Exception e) {
			LOGGER.error("Error occurred while retrieving all tasks", e);
			throw new ServerException("Error occurred while retrieving all tasks", e);
		}
	}

	@Override
	public Task updateTask(Long id, Task task) throws ServerException {
		LOGGER.info("TaskServiceImpl.updateTask() - ID [{}] with task [{}]", id, task);

		try {
			Optional<Task> existingTask = genaralDao.findById(id);
			if (existingTask.isPresent()) {
				Task t = existingTask.get();
				t.setTitle(task.getTitle());
				t.setDescription(task.getDescription());
				t.setDueDate(task.getDueDate());
				t.setCompletionStatus(task.getCompletionStatus());
				return genaralDao.save(t);
			} else {
				return null; 
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while updating task", e);
			throw new ServerException("Error occurred while updating task", e);
		}
	}

	@Override
	public boolean deleteTask(Long id) throws ServerException {
		LOGGER.info("TaskServiceImpl.deleteTask() - ID [{}]", id);

		try {
			if (genaralDao.existsById(id)) {
				genaralDao.deleteById(id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while deleting task", e);
			throw new ServerException("Error occurred while deleting task", e);
		}
	}

}
