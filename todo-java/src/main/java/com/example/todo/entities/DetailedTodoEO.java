package com.example.todo.entities;

import java.util.Objects;

/**
 * Entité Detailed To-do qui possède la totalité des informations lié à une to-do.
 * @author hbziouet
 *
 */
public class DetailedTodoEO extends TodoEO {
	
	
	/**
	 * Description de la to-do.
	 */
	private String description;

	public DetailedTodoEO(long id, String title, boolean done, String description) {
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
		DetailedTodoEO other = (DetailedTodoEO) obj;
		return Objects.equals(description, other.description);
	}
	
	
}
