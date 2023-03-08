package com.example.todo.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.todo.models.DetailedTodoDTO;
import com.example.todo.models.TodoDTO;

/**
 * Mapper qui convertit une to-do détaillé en todo.
 * @author hbziouet
 *
 */
@Mapper(componentModel = "spring")
public interface DetailedTodo2TodoMapper {
	
	TodoDTO convert(DetailedTodoDTO dto);
	List<TodoDTO> convert(List<DetailedTodoDTO> dtos);
}
