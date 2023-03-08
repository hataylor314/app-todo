package com.example.todo.models;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

/**
 * Vue d'une to-do détaillée.
 * @author hbziouet
 *
 */
@Validated
public class DetailedTodoDTO extends TodoDTO {
	
	/**
	 * Description de la to-do.
	 */
	private String description;

	public DetailedTodoDTO() {
		
	}
	public DetailedTodoDTO(long id, String title, boolean done, String description) {
		super(id, title, done);
		this.description = description;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetailedTodoDTO other = (DetailedTodoDTO) obj;
		return Objects.equals(description, other.description);
	}
	
	

}
