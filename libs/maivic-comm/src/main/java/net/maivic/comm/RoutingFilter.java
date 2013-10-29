package net.maivic.comm;

public interface  RoutingFilter  <T> {
	boolean route(T topic );
}
