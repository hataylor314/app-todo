package com.example.todo.mappers;

import org.mapstruct.Mapper;

import com.example.todo.entities.DetailedTodoEO;
import com.example.todo.models.DetailedTodoDTO;

@Mapper(componentModel = "spring")
public interface DetailedTodoMapper {
	
	DetailedTodoEO convert(DetailedTodoDTO dto);
	
	DetailedTodoDTO convert(DetailedTodoEO eo);
}
