package net.maivic.comm.RPC;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import net.maivic.comm.LazyResponse;


public class RelationHandler {
	private static InvocationHandler myHandler= null;
	private static Map<Class<?>, Object> impl_cache = new HashMap<Class<?>, Object>();
	public static class RelationInvocationHandler implements InvocationHandler {

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			return null;
		}
		
	}
	public static <T>  T get( Class<T> interface_def ){
		synchronized (RelationInvocationHandler.class) {
			if (myHandler == null){
				myHandler = new RelationHandler.RelationInvocationHandler();
			}
		}
		T ret= (T) impl_cache.getOrDefault(interface_def, null);
		if(ret != null) {
			return ret;
		}
		if (! interface_def.isInterface()) {
			throw new IllegalArgumentException("The interface_def must be a Class type of an interfacen not " + interface_def.getName() + "! ");
		}
		Proxy.newProxyInstance(interface_def.getClassLoader(), new Class<?> []{interface_def}, myHandler);
		return ret;
	}
	
	
	
	
}
