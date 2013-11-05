package net.maivic.comm.impl;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import net.maivic.comm.DefaultLazyResponse;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Maivic.FunctionReturn;
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

	Map<LazyResponse<?>, Class<?>> expectedResults = new HashMap<LazyResponse<?>, Class<?>>();
	public RPCInvocationHandler() {
		super((Integer)Context.get().get("ServiceID.RPC"));
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
			FunctionCall.Builder call = FunctionCall.newBuilder();
			
			for (int i = 0; i<method.getParameterCount(); i++) {
				Parameter p = method.getParameters()[i];
				call.addArgs(BaseTypeMapper.toBaseType(args[i], p.getType()));
			}
			MessageContainer.Builder container = MessageContainer.newBuilder();
			call.setFunction(getIndexForFunctionName());
			//LazyResponse<T> result = this.send(container.build());
			LazyResponse<?> lazy_response = null;
			synchronized (this.expectedResults) {
				lazy_response= this.send(call.build());
				expectedResults.put(lazy_response,method.getReturnType());
			}
			if (method.getReturnType().isAssignableFrom(LazyResponse.class)){
				return lazy_response;
			} else {
				return lazy_response.get();
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
		Class ret_type = null;
		synchronized (this.expectedResults) {
			ret_type= expectedResults.remove(response);
		}
		return BaseTypeMapper.fromBaseType(value, ret_type);
	}
	@Override
	protected Throwable convertException(ExceptionType exception,
			DefaultLazyResponse response) {
		return new IOException("RPC Invocation exception: " + exception.getMessage()+"of Type: " + exception.getTypeId());
	}
}