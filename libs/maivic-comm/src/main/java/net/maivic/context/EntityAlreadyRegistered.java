package net.maivic.context;

public class EntityAlreadyRegistered extends EntityException {

	public EntityAlreadyRegistered(String entity) {
		super(entity + " already registered");		
	}

}
