package com.example.todo.test.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.todo.models.DetailedTodo;
import com.example.todo.services.TodoService;

public class TodoServiceTest {
	
	private TodoService todoService;

    @BeforeEach
    public void setUp() {
        todoService = new TodoService();
    }

    @Test
    public void testGetAll() {
        List<DetailedTodo> todos = todoService.getAll();
        Assertions.assertEquals(3, todos.size());
    }

    @Test
    public void testAddTodo() {
        DetailedTodo newTodo = new DetailedTodo();
        newTodo.setTitle("Test new todo");
        newTodo.setDescription("No description");

        todoService.addTodo(newTodo);

        DetailedTodo addedTodo = todoService.getTodoById(newTodo.getId());

        Assertions.assertNotNull(addedTodo);
        Assertions.assertEquals(newTodo.getTitle(), addedTodo.getTitle());
        Assertions.assertEquals(newTodo.getDescription(), addedTodo.getDescription());
        Assertions.assertFalse(addedTodo.isDone());
    }

    @Test
    public void testUpdateTodoState() {
        DetailedTodo todo = todoService.getAll().get(0);

        todoService.updateTodoState(todo.getId(), true);

        DetailedTodo updatedTodo = todoService.getTodoById(todo.getId());

        Assertions.assertTrue(updatedTodo.isDone());
        Assertions.assertEquals(todo.getTitle(), updatedTodo.getTitle());
        Assertions.assertEquals(todo.getDescription(), updatedTodo.getDescription());
    }

    @Test
    public void testGetTodoById() {
        DetailedTodo todo = todoService.getAll().get(0);

        DetailedTodo retrievedTodo = todoService.getTodoById(todo.getId());

        Assertions.assertNotNull(retrievedTodo);
        Assertions.assertEquals(todo.getTitle(), retrievedTodo.getTitle());
        Assertions.assertEquals(todo.getDescription(), retrievedTodo.getDescription());
    }
}
