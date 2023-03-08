package com.example.todo.entities;

import java.util.Objects;


/**
 * Entité To-do qui possède l'état de la to-do et son titre.
 * @author hbziouet
 *
 */
public class TodoEO {
	
	/**
	 * Identifiant unique de la to-do.
	 */
	private long id;
	
	/**
	 * Titre de la to-do.
	 */
	private String title;
	
	
	/**
	 *  Etat de la to-do.
	 */
	private boolean done;
	
	public TodoEO(long id, String title, boolean done) {
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
		TodoEO other = (TodoEO) obj;
		return done == other.done && id == other.id && Objects.equals(title, other.title);
	}

	
}
