package com.example.todo.models;

import java.util.Objects;

import javax.validation.constraints.NotBlank;


/**
 * Vue d'une Todo composée d'un titre et de son état.
 * @author hbziouet
 *
 */
public class TodoDTO {
	
	/**
	 * Identifiant de la to-do
	 */
	private long id;
	
	/**
	 * Titre de la to-do
	 */
	@NotBlank(message = "Le titre doit être obligatoire")
	private String title;
	
	/**
	 * Etat de la to-do
	 */
	private boolean done;
	
	public TodoDTO() {
		
	}
	
	public TodoDTO(long id, String title, boolean done) {
		super();
		this.id = id;
		this.title = title;
		this.done = done;
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

	@Override
	public int hashCode() {
		return Objects.hash(done, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TodoDTO other = (TodoDTO) obj;
		return done == other.done && id == other.id && Objects.equals(title, other.title);
	}

	
}
