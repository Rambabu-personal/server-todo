package com.example.todo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.pojo.Task;

public interface GenaralDao extends JpaRepository<Task, Long>{

}
