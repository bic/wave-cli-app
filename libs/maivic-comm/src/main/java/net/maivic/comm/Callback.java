package net.maivic.comm;

public interface Callback <T> {
	void call(T result);
	void onError(Throwable t);
}
