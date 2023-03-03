package com.example.todo.ws.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.mappers.TodoMapper;
import com.example.todo.models.Todo;
import com.example.todo.services.TodoService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import static com.example.todo.ws.TodoConfiguration.WS_TODO_PATH;

@RestController
@Api(tags = "Todo")
@RequestMapping(WS_TODO_PATH)
@CrossOrigin
public class TodoWS {
	
	@Autowired
	private TodoService todoService;
	
	@Autowired 
	TodoMapper mapper;
	
	@GetMapping(value= "/all" ,produces = { "application/json" })
	@Operation(operationId = "getAllTodos", summary = "Récupère la liste des todo.", description = "Récupère la liste des todo.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Todo.class)))),
			@ApiResponse(responseCode = "500", description = "Erreur interne.") })
	public List<Todo> getAll(){
		return mapper.toTodo(todoService.getAll());
	}
	
	@PutMapping(value= "/{id}")
	@Operation(operationId = "updateTodoState", summary = "Change l'état d'une todo.", description = "Change l'état d'une todo.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK."),
			@ApiResponse(responseCode = "500", description = "Erreur interne.") })
	public void updateTodoState(@PathVariable long id, @RequestParam("state") boolean state){
		todoService.updateTodoState(id, state);
	}
}
