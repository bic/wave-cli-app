package net.maivic.context;

public class WrongType extends EntityException {
	public WrongType(Object o, Class<?> expectedType) {
		super("Object" + o.toString() + "is not of type " + expectedType.getName());
	}
}
