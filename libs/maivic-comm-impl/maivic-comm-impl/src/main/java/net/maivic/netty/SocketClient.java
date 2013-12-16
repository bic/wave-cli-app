package net.maivic.netty;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.google.protobuf.InvalidProtocolBufferException;

import net.maivic.comm.Maivic.MaivicServices;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.context.Context;
import net.maivic.context.EntityAlreadyRegistered;
import net.maivic.context.Log;
import net.maivic.context.UnsupportedType;
import net.maivic.context.WrongType;
import net.maivic.netty.MessageContainerInboundAdapter.IncomingCallBack;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

public class SocketClient {
	public static final Log log = new Log();
	public static final String TAG="SocketClient";
	Bootstrap b = null;
	public SocketClient(EventLoopGroup group) {
		this.init(group);
	}
	private IncomingCallBack incomingCallBack;
	private void init( EventLoopGroup group  ){
		b = new Bootstrap();
		b.group( group);
		b.channel(NioSocketChannel.class);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		this.incomingCallBack=new IncomingCallBack() {
			public void onIncomingMessage(MessageContainer msg) {
				log.i(TAG, "Received Messagecontainer with service_id: " +
						Integer.toString(msg.getServiceId()) + 
						" with a content length of  " + Integer.toString(msg.getContent().size()) );
				if ( msg.getServiceId() == 1) {
					try {
						MaivicServices services = MaivicServices.parseFrom(msg.getContent());
						for (int i = 0 ; i < services.getServiceIdsCount(); i++){
							log.i(TAG, "Service id: " + services.getServiceIds(i) + " Name: " + services.getServiceNames(i));
						}
					} catch (InvalidProtocolBufferException e) {
						log.e(TAG, "Could not deserialize MaivicServices for Messagecontainer with service_id 1",e);
					}
					
				}
			}
		};
		b.handler(new ChannelInitializer<SocketChannel>(){
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				MessageContainerInboundAdapter message_container_inbound_adapter = new MessageContainerInboundAdapter();
				message_container_inbound_adapter.setCb(SocketClient.this.incomingCallBack);
				ch.pipeline().addLast(new MessageContainerDecoder(), message_container_inbound_adapter);
				ch.pipeline().addFirst(new ProtobufVarint32FrameDecoder());
				ch.pipeline().addLast( new MessageContainerEncoder(), new MessageContainterOutboundAdapter());
			}
		});
	}
	/**
	 *  connect opens a connection to a listening host.
	 *  Once connected the send operations are schedueld on the {@link EventLoopGroup} supplied with the {@link #SocketClient(EventLoopGroup)} constructor.
	 *  Incoming {@link MessageContainer} objects are forwarded to the MessageCont
	 * @param host The host to connect to
	 * @param port adapter of the connection
	 * @param adapter Adaper used for incoming connections
	 * @return
	 */
	
	public ChannelFuture connect(String host, int port, IncomingCallBack cb) {
		if(b==null) {
			throw new IllegalStateException("Socketclient may not have it's bootstrap set to null");
		}
		if(cb != null) {
			this.incomingCallBack=cb;
		}
		if (port  < 0) {
			port = (Integer) Context.get().get("default.nettytcp.port");
		}
		
		return this.b.connect(host,port);
	}
	public static SocketClient get() {
		Context c =Context.get();
		Object instance = null;
		synchronized (c) {
			instance = c.get(SocketClient.class.getName());
			if (instance instanceof Class) {
				EventLoopGroup group = c.get(ScheduledExecutorService.class.getName());
				Class<? extends SocketClient> clz= (Class<? extends SocketClient>)instance;
				for (Constructor<?> constructor: clz.getConstructors()) {
					if (constructor.getParameterCount() ==1 && 
						constructor.getParameters()[0].getType().isAssignableFrom(group.getClass())){
						try {
							instance = constructor.newInstance(group);
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
				if (instance != null && SocketClient.class.isAssignableFrom(instance.getClass())) {
					try {
						c.register(SocketClient.class.getName(), instance,true);
						if(c.get(ScheduledExecutorService.class.getName()) == null){
							c.register(ScheduledExecutorService.class.getName(), group);
						}
					} catch (EntityAlreadyRegistered e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WrongType e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedType e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			if (instance==null){
				EventLoopGroup group = c.get(ScheduledExecutorService.class.getName()); 
				instance = new SocketClient(group);
				c.setSupportedEntity(SocketClient.class.getName(), SocketClient.class);
			}
		}
		return (SocketClient)instance;
	}
	
}
