package net.maivic.comm.impl;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.IsInstanceOf;

import net.maivic.comm.Callback;
import net.maivic.comm.DefaultLazyResponse;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Maivic.BaseType;
import net.maivic.comm.Maivic.ExceptionType;
import net.maivic.comm.Maivic.FunctionCall;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.Relation;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.Transport;
import net.maivic.comm.service.AbstractRPCServiceClient;
import net.maivic.context.Context;


class RPCInvocationHandler extends  AbstractRPCServiceClient implements InvocationHandler  {
	Map<LazyResponse<?>, Type> expectedResults = new HashMap<LazyResponse<?>, Type>();
	public RPCInvocationHandler() {
		super((Integer)Context.get().get("ServiceID.RPC"));
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		if (method.equals(RPCUpdateSubscriptionService.class.getMethod("unsubscribe"))) {
			this.unsubscribeRPC();
			return null;
		}
		try{
			
			if(Class.forName("net.maivic.protocol.relations.OfferRelations").isAssignableFrom(proxy.getClass())){
				int i =5;
				int j = i+1;
				j++;
						
			}

			
			
			List<Integer> callbackparams = new ArrayList<Integer>();
			{
				int i = 0;
				for( Annotation[] annotations : method.getParameterAnnotations()){
					for(Annotation annotation : annotations){
						if (SubscriptionCallback.class.isInstance(annotation)){
							callbackparams.add(i);
							break;
						}
					}
					i++;
				}
			}
			
			Class<?> parentInterface=proxy.getClass().getInterfaces()[0];
			Table tbl_annotation = parentInterface.getAnnotation(Table.class);
			Callback<?>[] callbacks = new Callback<?>[callbackparams.size()];
			Class<?>[] otherArgTypes = new Class<?>[args.length-callbackparams.size()];
			Object[] otherArgs = new Object[args.length-callbackparams.size()];	
			for(int i=0, j =0, k=0; i < args.length; i++) {
				if(callbackparams.contains(i)){
					callbacks[j] = (Callback)args[i];
					j++;
				} else {
					otherArgs[k] = args[i];
					otherArgTypes[k]=method.getParameterTypes()[i];
					k++;
				}
			}
			
			FunctionCall.Builder call = FunctionCall.newBuilder();
			//TODO: only apply if callback argument is supplied
			
			
			call.setIsSubscription(true);
			
			for (int i = 0; i<otherArgTypes.length; i++) {
				call.addArgs(BaseTypeMapper.toBaseType(otherArgs[i], otherArgTypes[i]));
			}
			MessageContainer.Builder container = MessageContainer.newBuilder();
		
			call.setFunction(parentInterface.getCanonicalName() + "." + method.getName());
			String funcName=null;
			
			if (tbl_annotation == null) {
				funcName =proxy.getClass().getInterfaces()[0].getCanonicalName() + "." + method.getName();
			}else {
				funcName =  (String)Context.get().get("RelationsRPC.prefix") ;
				funcName += "." + tbl_annotation.value();
				Relation method_annotation=method.getAnnotation(Relation.class);
				if(method_annotation==null){
					funcName += ".JAVAMETHOD." + method.getName();
				}else {
					funcName += "." + method_annotation.name();
				}
			}
			if (callbacks.length >0) {
				call.setIsSubscription(true);
			}
			call.setFunction(funcName);
			//LazyResponse<T> result = this.send(container.build());
			LazyResponse<?> lazy_response = null;
			synchronized (this.expectedResults) {
				if (call.hasIsSubscription() && call.getIsSubscription()){
					
				}
				lazy_response= this.send(call.build(), callbacks);
				expectedResults.put(lazy_response,method.getGenericReturnType());
			}
			if (method.getReturnType().isAssignableFrom(LazyResponse.class)){
				return lazy_response;
			} else {
				return lazy_response.get();
			}
		}
		catch (Throwable t){
			t.printStackTrace();
			throw t;
		}
	}
	private void unsubscribeRPC() {
		// TODO Auto-generated method stub
		
	}
	private int getIndexForFunctionName() {		
		return 0;
	}
	@Override
	protected Transport<MessageContainer> getTransport() {
		return Context.get().getTransportManager().getTransports().get(0);
	}
	@Override
	protected Object convertResult(BaseType value,
			DefaultLazyResponse response) throws IOException {
		ParameterizedType para_ret_type = null;
		Type ret_type=null;
		synchronized (this.expectedResults) {
			ret_type=  expectedResults.get(response);
		}
		if (ret_type instanceof ParameterizedType && ((ParameterizedType)ret_type).getRawType() == LazyResponse.class) {
			para_ret_type= (ParameterizedType) ret_type;
			ret_type= para_ret_type.getActualTypeArguments()[0];
		
		}
		
	
		Object ret = BaseTypeMapper.fromBaseType(value, ret_type);
		synchronized (this.expectedResults) {
			expectedResults.remove(response);
		}
		return ret;
			
		
	}
	@Override
	protected Throwable convertException(ExceptionType exception,
			DefaultLazyResponse response) {
		return new IOException("RPC Invocation exception: " + exception.getMessage());
	}
}