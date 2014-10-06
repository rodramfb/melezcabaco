package ar.com.syswarp.entity;

public abstract class AbstractEntity {

	public abstract String validateCreate();
	public abstract String validateUpdate();
	
	protected String buildNullFieldError(String field) {
		return String.format(
				"Error: No se puede dejar sin datos (nulo) el campo: %s", 
				field);
	}

	protected String buildEmptyFieldError(String field) {
		return String.format(
				"Error: No se puede dejar vacio el campo: %s", 
				field);
	}
	
}
