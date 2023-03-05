package com.example.todo.services;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entities.DetailedTodoEO;
import com.example.todo.exception.TodoException;
import com.example.todo.mappers.DetailedTodoMapper;
import com.example.todo.models.DetailedTodoDTO;
import com.example.todo.repositories.TodoRepository;

@Service
public class TodoService {
		
	@Autowired
	private TodoRepository repo;
	
	@Autowired
	private DetailedTodoMapper detailedTodoMapper;
			
	public TodoService(TodoRepository repo, DetailedTodoMapper detailedTodoMapper) {
		this.repo = repo;
		this.detailedTodoMapper = detailedTodoMapper;
	}

	public List<DetailedTodoDTO> getAll(){
		return repo.getAll().stream().map(t -> detailedTodoMapper.convert(t)).collect(Collectors.toList());
	}
	
	public void updateTodoState(long id, boolean done) throws TodoException {
		DetailedTodoEO todoEO = repo.getTodoById(id);
		if(todoEO == null) {
			throw new TodoException("Modification Todo impossible: l''élement à modifier est introuvable", 404);
		}
		repo.updateTodoState(todoEO, done);
    }
	
	public DetailedTodoDTO getTodoById(long id) throws TodoException {
		DetailedTodoEO todoEO = repo.getTodoById(id);
		
		if(todoEO == null) {
			throw new TodoException("Todo introuvable: Aucune Todo existante avec l'id :" + id , 404);
		}
		
		return detailedTodoMapper.convert(todoEO);
    }
	
	public void addTodo(DetailedTodoDTO todo) {
		repo.addTodo(detailedTodoMapper.convert(todo));
	}
}
