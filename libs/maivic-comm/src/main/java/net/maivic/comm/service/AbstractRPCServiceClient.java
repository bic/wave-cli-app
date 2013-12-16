package net.maivic.comm.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.maivic.comm.Callback;
import net.maivic.comm.DefaultLazyResponse;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Maivic.BaseType;
import net.maivic.comm.Maivic.ExceptionType;
import net.maivic.comm.Maivic.FunctionCall;
import net.maivic.comm.Maivic.FunctionReturn;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.comm.Maivic.MessageContainer.Builder;
import net.maivic.comm.Maivic.ThreadControl;
import net.maivic.comm.Maivic.ThreadControl.Operation;
import net.maivic.comm.RoutingFilter;
import net.maivic.comm.ThreadManager;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.context.Context;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public  abstract  class AbstractRPCServiceClient<T> {
	private int serviceId;
	private Map<Integer,DefaultLazyResponse<T>> openRequests = new HashMap<Integer, DefaultLazyResponse<T>>();
	ThreadManager threadManager = Context.get().getThreadManager();
	protected AbstractRPCServiceClient(int serviceId) {
		this.serviceId = serviceId;
		Context.get().getSubscriptionCenter().addSubscription(new RoutingFilter<MessageContainer>()
		{

			public boolean route(MessageContainer mess) {
				return mess.getServiceId() == AbstractRPCServiceClient.this.serviceId;
			}
		}, new Callback<MessageContainer>()  {

			public void call(MessageContainer result) {
				AbstractRPCServiceClient.this.handleIncoming(result);
			}
		});
	}
	protected void handleIncoming(MessageContainer result) {
		int thread_id=result.getThreadControl().getThreadId();
		
		if (threadManager.isActive(thread_id))
		{	
			DefaultLazyResponse<T> response = openRequests.get(thread_id);
			if (response == null) {
				this.handleIncomingWithoutSend(result);
			}
			try {
				FunctionReturn retValue = FunctionReturn.parseFrom(result.getContent());
				if(retValue.hasProgress()) {
					if(retValue.getProgress().hasMaxProgress()) {
						response.setMaxProgress((int)retValue.getProgress().getMaxProgress());
						response.setProgress(retValue.getProgress().getProgress());
					}
				}
					
				if(retValue.hasException()) {
					response.setFailure(this.convertException(retValue.getException(), response));
				}else{
					try {
						
						response.setSuccess((T)this.convertResult(retValue.getValue(), response));
					} catch (IOException e) {
						response.setFailure(e);
					}
				}
				
			
				
			} catch (InvalidProtocolBufferException e) {
				response.setFailure(e);
			} catch (Throwable e) {
				response.setFailure(e);
			}
			
			
		}
		
	}
	public int getServiceId() {
		return serviceId;
	}
	public LazyResponse<T> send(FunctionCall message) {
		Builder container = MessageContainer.newBuilder();
		fillMessageContainer(container, message);
		TransportManager tm = Context.get().getTransportManager();
		
	
		DefaultLazyResponse<T> lazyResponse = new DefaultLazyResponse<T>(); 
		final int thread_id_ = container.getThreadControl().getThreadId();
		openRequests.put(thread_id_, lazyResponse);
		lazyResponse.addSuccessCallback(new Callback<LazyResponse<T>>() {
			int thread_id= thread_id_;
			public void call(LazyResponse<T> result) {
				openRequests.remove(thread_id);
				threadManager.closeThread(thread_id);
			}});
		
		return tm.sendWithRetry((T)container,new FixedRetry<T>(FixedRetry.logRetryDelays(5), "nettytcp"), lazyResponse);
	}
	
	protected void fillMessageContainer(MessageContainer.Builder container, Message content){
		container.setServiceId(this.getServiceId());
		container.setThreadControl(ThreadControl.newBuilder().setThreadId(this.threadManager.newThread()).setOperation(Operation.DEFAULT));
		container.setContent(content.toByteString());
	}
	protected abstract Transport<MessageContainer> getTransport();
	protected void handleIncomingWithoutSend(MessageContainer result) {
		throw new UnsupportedOperationException("Received a Message which cannot be assigned to an open request!");
		
	}
	protected abstract T convertResult(BaseType value, DefaultLazyResponse<T> response) throws IOException;
	protected abstract Throwable convertException(ExceptionType exception, DefaultLazyResponse<T> response);
	
}
