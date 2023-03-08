package com.example.todo.mappers;

import org.mapstruct.Mapper;

import com.example.todo.entities.TodoEO;
import com.example.todo.models.TodoDTO;


/**
 * Mapper qui convertit une TodoDTO en entité et vice versa.
 * @author hbziouet
 *
 */
@Mapper(componentModel = "spring")
public interface TodoMapper {
	
	TodoEO convert(TodoDTO dto);
	TodoDTO convert(TodoEO eo);
}
