package net.maivic.context;

public class InvalidEntity extends EntityException {
	public InvalidEntity(String entity) {
		super("Entity " + entity + " is not supported!");
	}
}
