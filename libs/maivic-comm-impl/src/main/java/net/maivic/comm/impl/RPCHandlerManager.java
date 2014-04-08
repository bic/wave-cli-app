package net.maivic.comm.impl;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import net.maivic.comm.Table;


public class RPCHandlerManager {
	private static InvocationHandler invocationHandler = new RPCInvocationHandler();
	
	
	
	public static <T> T getHandler(Class<T> clz) {
		if (!clz.isInterface()) {
			throw new IllegalArgumentException("Argument must be an interface"); 
		} else if(!clz.isAnnotationPresent(RPCInterface.class) && ! clz.isAnnotationPresent(Table.class)) {
			throw new IllegalArgumentException( "RPC Interface is not annotated");
		}
		@SuppressWarnings("unchecked")
		T proxy = (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, RPCHandlerManager.invocationHandler);
		return proxy;
	}
	
	
}