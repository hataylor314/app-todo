package com.example.todo.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.todo.entities.DetailedTodoEO;

/**
 * Répository locale qui initialise les to-do et opérations métier.
 * @author hbziouet
 *
 */
@Repository
public class TodoRepository {
	
	
	/**
	 *  Liste des to-do.
	 */
	public List<DetailedTodoEO> todos = new ArrayList<>();
	
	/**
	 * Sequence d'identifiant to-do.
	 */
	private long idSequence;
	

	/**
	 * Constructor
	 */
	public TodoRepository() {
		todos.add(new DetailedTodoEO(generateId(), "Prepare food", false, "No description"));
	    todos.add(new DetailedTodoEO(generateId(), "Do laundry", false, "No description"));
		todos.add(new DetailedTodoEO(generateId(), "Call the school principal", false, "No description"));
	}
	
	
	/**
	 * Récupère toutes les to-do.
	 * @return
	 */
	public List<DetailedTodoEO> getAll(){
		return todos;
	}
	
	
	/**
	 * Met à jour l'état d'une to-do.
	 * @param todoToUpdate
	 * @param done
	 */
	public void updateTodoState(DetailedTodoEO todoToUpdate, boolean done) {
		
	  todoToUpdate.setDone(done);
      todos.remove(todoToUpdate);
      
      if (done) {
          todos.add(todoToUpdate);
      } else {
          todos.add(0, todoToUpdate);
      }

	}
	
	/**
	 * Récupère une to-do par son identifiant.
	 * @param id
	 * @return
	 */
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
	
	/**
	 * Génère un id unique pour chaque to-do.
	 * @return
	 */
	private long generateId() {
		return this.idSequence++;
	}

	
	
}
