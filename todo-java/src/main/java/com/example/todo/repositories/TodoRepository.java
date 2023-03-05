package com.example.todo.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.todo.entities.DetailedTodoEO;

@Repository
public class TodoRepository {
	
	public List<DetailedTodoEO> todos = new ArrayList<>();
	
	private long idSequence;
	

	public TodoRepository() {
		todos.add(new DetailedTodoEO(generateId(), "Prepare food", false, "No description"));
	    todos.add(new DetailedTodoEO(generateId(), "Do laundry", false, "No description"));
		todos.add(new DetailedTodoEO(generateId(), "Call the school principal", false, "No description"));
	}
	
	public List<DetailedTodoEO> getAll(){
		return todos;
	}
	
	public void updateTodoState(DetailedTodoEO todoToUpdate, boolean done) {
		
	  todoToUpdate.setDone(done);
      todos.remove(todoToUpdate);
      
      if (done) {
          todos.add(todoToUpdate);
      } else {
          todos.add(0, todoToUpdate);
      }

	}
	
	public DetailedTodoEO getTodoById(long id) {
      return todos.stream()
              .filter(todo -> todo.getId() == id)
              .findFirst()
              .orElse(null);
	}
	
	public void addTodo(DetailedTodoEO todo) {
		todo.setId(generateId());
		todo.setDone(false); // Set par défaut le false car création
		todos.add(0, todo);
	}
	
/* Utils */
	
	private long generateId() {
		return this.idSequence++;
	}

	
	
}
