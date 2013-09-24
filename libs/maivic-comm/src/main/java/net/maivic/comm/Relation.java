package net.maivic.comm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {
	enum RelationType{
		MANY_TO_ONE,
		ONE_TO_MANY
	}
	RelationType type();
	String name();
	
}
