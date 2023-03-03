package com.example.todo.services;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.mappers.TodoMapper;
import com.example.todo.models.DetailedTodo;
import com.example.todo.models.Todo;

@Service
public class TodoService {
	
	private List<DetailedTodo> todos = new ArrayList<>();

	@Autowired 
	TodoMapper mapper;
	
	private long idSequence;
	

	public TodoService() {
		this.todos.add(new DetailedTodo(generateId(), "Prepare food", false, "No description"));
		this.todos.add(new DetailedTodo(generateId(), "Do laundry", false, "No description"));
		this.todos.add(new DetailedTodo(generateId(), "Call the school principal", false, "No description"));
	}
	
	public List<Todo> getAll(){
		return mapper.toTodo(this.todos);
	}
	
	private long generateId() {
		return this.idSequence++;
	}


	
	
	
}
