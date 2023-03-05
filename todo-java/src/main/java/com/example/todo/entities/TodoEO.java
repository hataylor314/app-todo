package com.example.todo.entities;

import java.util.Objects;

public class TodoEO {
	
	private long id;
	
	private String title;
	
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
