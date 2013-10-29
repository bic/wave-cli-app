package net.maivic.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import net.maivic.comm.Callback;
import net.maivic.comm.DefaultLazyResponse;
import net.maivic.comm.Function;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.comm.SettableLazyResponse;
import net.maivic.comm.Transport;
import net.maivic.comm.exception.SendFailed;
import net.maivic.context.Context;
import net.maivic.context.Log;

public class NettyTransportImpl implements Transport<MessageContainer>{
	private static Log log = Context.get().log();
	private String TAG = NettyTransportImpl.class.toString();
	private SocketClient socketClient= null;
	private String host;
	private int port;
	private static final String[] transportTypes=new String[]{"nettytcp"};
	private String scheme = null;
	public NettyTransportImpl() {
		ResourceBundle bundle = (ResourceBundle) Context.get().get("Config");
		;
		this.port = (Integer) bundle.getObject("tcpip-port");
		this.init(bundle.getString("tcpip-uri"));
	}
	public NettyTransportImpl(String uri_string){
		this.init(uri_string);
	}
	private void init(String uri_string) {
		URI uri = URI.create(uri_string);
		String scheme = uri.getScheme(); 
		boolean accepted = false;
		for(String transportType : transportTypes){
			System.out.println(transportType);
			if(transportType.equals(scheme)){
				accepted=true;
				break;
			}
		}
		this.TAG="TransportImpl("+uri_string+")";
		if(!accepted) {
			throw new IllegalArgumentException("Scheme " + scheme + " not accepted!");
		}
		this.scheme = scheme;
		this.host=uri.getHost();
		this.port=uri.getPort();		
		socketClient = SocketClient.get();
		
	}
	public String getScheme() {
		return this.scheme;
	}

	public String[] getTransportTypes() {
		return transportTypes;
	}

	public void tearDown(int millis) {
		synchronized (this.beforeDisconnectCallback) {
			if(this.beforeDisconnectCallback!=null){
				this.beforeDisconnectCallback.call(this);
			}
			
		}
	
	}
	private Set<Channel> channels = new HashSet<Channel>();
	private Callback<Transport> afterConnectCallback=null; 
	private Callback<Transport> beforeDisconnectCallback=null;
	private ChannelFuture addListener;
	private List<Callback<MessageContainer>> callbacks = new ArrayList<Callback<MessageContainer>>();
	private class  SendListener implements GenericFutureListener<ChannelFuture> {
		private Set<Channel> chans= new HashSet<Channel>();
		private MessageContainer content;
		DefaultChannelPromise promise =null;
		SendFailed failedException=null; 
		private int retry=0;
		private final int MAX_RETRY=3;
		public SendListener( MessageContainer content, DefaultChannelPromise promise) {
			this.content=content;
			this.promise=promise;
		}
		public void operationComplete(ChannelFuture f) throws Exception {
			if (!f.isSuccess() && retry<= MAX_RETRY){
				log.w(TAG,"Could not connect on " + Integer.toString(retry+1) + "attempt. Cause:" , f.cause());
				retry ++;
				if (failedException==null){
					failedException = new SendFailed(f.cause());
				} else {
					failedException.addSuppressed(f.cause());
				}
				if (retry == MAX_RETRY){
					chans.add(f.channel());
					retry = -1;
				}
				synchronized (NettyTransportImpl.this.channels) {
					Channel c = null;
					c=channelSelect(content, chans);
					if (c==null){
						this.promise.setFailure(f.cause());
					}
					c.writeAndFlush(content).addListener(this);
					
				}
				
			} else if(!f.isSuccess()) {
				if(this.promise != null){
					this.promise.setFailure(failedException);
				}
			} else {
				//Success
				this.promise.setSuccess();
			}
		}
		
	}
	
	public void pullUp() {
		if (this.afterConnectCallback != null){
			synchronized (this.afterConnectCallback) {
				if(this.afterConnectCallback != null){
					this.afterConnectCallback.call(this);
				}
			}
		}
		ChannelFuture ret = this.socketClient.connect(this.host, this.port);
		ret.addListener(new GenericFutureListener<Future<? super Void>>(){
			public void operationComplete(Future<? super Void> f)
					throws Exception {
				channels.add(((ChannelFuture)f).channel());	
			}
		});
		
		ret.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
		
			public void operationComplete(Future<? super Void> f)
					throws Exception {
				synchronized (channels) {
					channels.remove(f);	
				}
				
				
			}
			
		});
	}

	public boolean isConnected() {
		return channels.isEmpty();
	}

	public void cbBeforeDisconnect(Callback<Transport> cb) {
		this.beforeDisconnectCallback=cb;

	}

	public void cbAfterConnect(Callback<Transport> cb) {
		this.afterConnectCallback =cb;
	}
	
	public String getUri() {
		return "tcp://" + host + ":" + Integer.toString(this.port);
	}
	private Channel  channelSelect( MessageContainer container,Set<Channel> exclude) {
		for (Channel c : channels){
			if (exclude == null || !exclude.contains(c)){
				return c;
			}
		}
		return null;
	}
	
	
	private class ResponseListener <T> implements GenericFutureListener<Future<? super Void>> {
		private SettableLazyResponse<T> response;
		public ResponseListener(SettableLazyResponse<T> response) {
			this.response = response;
		}
		public void operationComplete(Future<? super Void> future)
				throws Exception {
			if(future.isSuccess()) {
				// Succeeded in sending. Do nothing
			}  else {
				this.response.setFailure(future.cause());
			}
			
		}
	}	 
	public SettableLazyResponse<MessageContainer> send(MessageContainer container) {
		DefaultLazyResponse<MessageContainer> retval = new DefaultLazyResponse<MessageContainer>();
		this.send(container, retval,0);
		return retval;
	}
		
	

	
	public void registerCallback(Callback<MessageContainer> cb) {
		this.callbacks .add(cb);

	}
	public  void send(final MessageContainer container,
			SettableLazyResponse<MessageContainer> lazyResponse, long sendAfter) {
		
		
		
		long delay = sendAfter - System.currentTimeMillis();
		if(delay > 0) {
			EventLoopGroup g = (EventLoopGroup) Context.get().get(EventLoopGroup.class.getName());
			g.schedule(new Runnable() {
				Channel c = channelSelect(container,null);
				DefaultChannelPromise promise = new DefaultChannelPromise(c);
				SendListener s = new SendListener(container, promise);
				ChannelFuture ret;
				public void run() {
					c.writeAndFlush(container).addListener(s);
				}
			} , delay, TimeUnit.MILLISECONDS);
		} else {
			Channel c = channelSelect(container,null);
			DefaultChannelPromise promise = new DefaultChannelPromise(c);
			SendListener s = new SendListener(container, promise);
			ChannelFuture ret = c.writeAndFlush(container).addListener(s);
			ret.addListener( new ResponseListener<MessageContainer>(lazyResponse));
		}
	}
	

}