package com.example.todo.services;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todo.models.DetailedTodo;

@Service
public class TodoService {
	
	private List<DetailedTodo> todos = new ArrayList<>();
	
	private long idSequence;
	

	public TodoService() {
		this.todos.add(new DetailedTodo(generateId(), "Prepare food", false, "No description"));
		this.todos.add(new DetailedTodo(generateId(), "Do laundry", false, "No description"));
		this.todos.add(new DetailedTodo(generateId(), "Call the school principal", false, "No description"));
	}
	
	public List<DetailedTodo> getAll(){
		return this.todos;
	}
	
	public void updateTodoState(long id, boolean done) {
		DetailedTodo todo = getTodoById(id);
        if (todo != null) {
            todo.setDone(done);
            todos.remove(todo);
            if (done) {
                todos.add(todo);
            } else {
                todos.add(0, todo);
            }
        }
    }
	
	public DetailedTodo getTodoById(long id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElse(null);
    }
	
	private long generateId() {
		return this.idSequence++;
	}


	
	
	
}
