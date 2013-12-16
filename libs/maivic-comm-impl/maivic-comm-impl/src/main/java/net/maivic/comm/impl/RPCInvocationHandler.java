package net.maivic.comm.impl;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import net.maivic.comm.DefaultLazyResponse;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Maivic.FunctionReturn;
import net.maivic.comm.Relation;
import net.maivic.comm.Table;
import net.maivic.comm.Transport;
import net.maivic.comm.Maivic.BaseType;
import net.maivic.comm.Maivic.BaseTypeOrBuilder;
import net.maivic.comm.Maivic.ExceptionType;
import net.maivic.comm.Maivic.FunctionCall;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.comm.service.AbstractRPCServiceClient;
import net.maivic.context.Context;

import com.google.protobuf.ByteString;


class RPCInvocationHandler extends  AbstractRPCServiceClient implements InvocationHandler  {
	Map<LazyResponse<?>, Type> expectedResults = new HashMap<LazyResponse<?>, Type>();
	public RPCInvocationHandler() {
		super((Integer)Context.get().get("ServiceID.RPC"));
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		try{
			FunctionCall.Builder call = FunctionCall.newBuilder();
			
			for (int i = 0; i<method.getParameterCount(); i++) {
				Parameter p = method.getParameters()[i];
				call.addArgs(BaseTypeMapper.toBaseType(args[i], p.getType()));
			}
			
			
			MessageContainer.Builder container = MessageContainer.newBuilder();
			call.setFunction(proxy.getClass().getInterfaces()[0].getCanonicalName() + "." + method.getName());
			Table tbl_annotation = proxy.getClass().getInterfaces()[0].getAnnotation(Table.class);
			
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
			call.setFunction(funcName);
			//LazyResponse<T> result = this.send(container.build());
			LazyResponse<?> lazy_response = null;
			synchronized (this.expectedResults) {
				lazy_response= this.send(call.build());
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