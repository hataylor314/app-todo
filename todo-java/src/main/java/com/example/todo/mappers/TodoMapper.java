package com.example.todo.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.todo.models.DetailedTodo;
import com.example.todo.models.Todo;

@Mapper(componentModel = "spring")
public interface TodoMapper {
	
	List<Todo> toTodo(List<DetailedTodo> detailedTodo);
}
