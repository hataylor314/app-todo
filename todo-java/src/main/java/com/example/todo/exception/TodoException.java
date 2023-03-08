package com.example.todo.exception;

/**
 * Exception to-do.
 * @author hbziouet
 *
 */
public class TodoException extends Exception{
	
	private static final long serialVersionUID = 1L;

	private final Integer httpCode;

	/**
	 * Constructeur.
	 * 
	 * @param message  le message d'erreur.
	 * @param httpCode le code http à l'exception, peut-être null.
	 */
	public TodoException(String message, Integer httpCode) {
		super(message);
		validateHttpCode(httpCode);
		this.httpCode = httpCode;
	}

	/**
	 * Constructeur.
	 * 
	 * @param message  le message d'erreur.
	 * @param cause    la cause de l'exception.
	 * @param httpCode le code http à l'exception, peut-être null.
	 */
	public TodoException(String message, Throwable cause, Integer httpCode) {
		super(message, cause);
		validateHttpCode(httpCode);
		this.httpCode = httpCode;
	}

	
	/**
	 * Méthode de validation du http code
	 * @param httpCode
	 */
	private void validateHttpCode(Integer httpCode) {
		if (httpCode != null && httpCode < 400) {
			throw new IllegalArgumentException(
					"Le code http d'une exception Todo doit être supérieur ou égal à 400 s'il est renseigné.");
		}
	}

	/**
	 * Méthode qui récupère le http code
	 * @return
	 */
	public Integer getHttpCode() {
		return httpCode;
	}
}
