package net.maivic.com.netty;
	import java.io.IOException;

>>>>>>> 8b47bffe8461e507accc8cc363cb40ba90900c80
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.io.IOException;

import main.java.net.maivic.netty.SocketClient;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.comm.PlatformSupport;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.context.Context;
<<<<<<< HEAD

import com.google.protobuf.ByteString;

public class NettyClientConnector {

	@Test
	public void testNettyTransport() {
		TransportManager man = Context.get().getTransportManager();
		Transport<MessageContainer> nettyTransport = man
				.addTransport("nettytcp://localhost:12345");
=======
import net.maivic.context.UnsupportedType;
import net.maivic.context.WrongType;
import net.maivic.netty.SocketClient;

import org.junit.Test;

import com.google.protobuf.ByteString;


public class NettyClientConnector{
	@Test
	public void testNettyTransport() {
		
		TransportManager man= Context.get().getTransportManager();
		Transport<MessageContainer> nettyTransport = man.addTransport("nettytcp://localhost:12345");
		nettyTransport.pullUp();

	}

	public void testSocketClient() {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			SocketClient s = new SocketClient(group);
			ChannelFuture f = s.connect("localhost", 12345, null);
			f.await();
			System.out.print("future returned!");
			if (f.isDone() && !f.isSuccess()) {
				TestCase.assertTrue("Connection aborted: "
						+ f.cause().getMessage(), false);
			}

			MessageContainer send = generateContainer();
			try {
				send.writeTo(System.out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			f.channel().writeAndFlush(send);
			ChannelFuture x = f.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			System.out.print("Connection aborted: " + e.getMessage());
			assert false;
		} finally {
			group.shutdownGracefully();
		}
	}

	MessageContainer generateContainer() {
		MessageContainer ret = MessageContainer.newBuilder().setServiceId(1)
				.setContent(ByteString.copyFrom("blacontent".getBytes()))
				.build();
		return ret;
	}

}
