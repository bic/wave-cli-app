package net.maivic.context;

public class UnsupportedType extends EntityException {

	public UnsupportedType(String string) {
		super("Type "+string+ "is not supported");
	}

}
