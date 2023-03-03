package com.example.todo.models;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

@Validated
public class DetailedTodo {
	
	private long id;
	
	@NotBlank(message = "Le titre de la Todo est obligatoire")
	private String title;
	
	private boolean done;
	
	private String description;
	
	public DetailedTodo(long id, String title, boolean done, String description) {
		this.id = id;
		this.title = title;
		this.done = done;
		this.description = description;
	}
	
	public DetailedTodo() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, done, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetailedTodo other = (DetailedTodo) obj;
		return Objects.equals(description, other.description) && done == other.done && id == other.id
				&& Objects.equals(title, other.title);
	}
	
	
	
	

}
