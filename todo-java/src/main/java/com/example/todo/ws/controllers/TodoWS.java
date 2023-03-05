package com.example.todo.ws.controllers;


import static com.example.todo.ws.TodoConfiguration.WS_TODO_PATH;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.exception.TodoException;
import com.example.todo.mappers.DetailedTodo2TodoMapper;
import com.example.todo.models.DetailedTodoDTO;
import com.example.todo.models.TodoDTO;
import com.example.todo.services.TodoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Api(tags = "Todo")
@RequestMapping(WS_TODO_PATH)
@CrossOrigin
public class TodoWS {
	
	@Autowired
	private TodoService todoService;
	
	@Autowired 
	DetailedTodo2TodoMapper detailedTodo2TodoMapper;
	
	@GetMapping(value= "/all" ,produces = { "application/json" })
	@ApiOperation(value = "Récupère la liste des todo.", nickname = "getAllTodos", notes = "Récupère la liste des todo.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TodoDTO.class)))),
			@ApiResponse(responseCode = "500", description = "Erreur interne.") })
	public ResponseEntity<List<TodoDTO>> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(detailedTodo2TodoMapper.convert(todoService.getAll()));
	}
	
	@GetMapping(value= "/{id}" ,produces = { "application/json" })
	@ApiOperation(value = "Récupère la todo par son id.", nickname = "getTodoById", notes = "Récupère la todo par son id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK.", content = @Content(mediaType = "application/json",schema = @Schema(implementation = DetailedTodoDTO.class))),
			@ApiResponse(responseCode = "500", description = "Erreur interne.") })
	public ResponseEntity getById(@PathVariable long id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoById(id));
		}catch(TodoException ex) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	@PostMapping(consumes = {"application/json"}, produces = {"application/json"})
	@ApiOperation(value = "Ajoute une nouvelle Todo.", nickname = "addTodo", notes = "Ajoute une nouvelle Todo.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetailedTodoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad request."),
			@ApiResponse(responseCode = "500", description = "Erreur interne.") })
    public ResponseEntity addTodo(@RequestBody @Valid DetailedTodoDTO todo) {
		try {
			todoService.addTodo(todo);
			return ResponseEntity.status(HttpStatus.OK).body(true);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
    }
	
	@PutMapping(value= "/{id}")
	@ApiOperation(value = "Change l'état d'une todo.", nickname = "updateTodoState", notes = "Change l'état d'une todo.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK."),
			@ApiResponse(responseCode = "500", description = "Erreur interne.") })
	public ResponseEntity updateTodoState(@PathVariable long id, @RequestParam("state") boolean state){
		try {
			todoService.updateTodoState(id, state);
			return ResponseEntity.status(HttpStatus.OK).body(true);
		}catch(TodoException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}