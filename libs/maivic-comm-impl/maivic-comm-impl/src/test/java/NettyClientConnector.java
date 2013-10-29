	import java.io.IOException;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import junit.framework.TestCase;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.netty.SocketClient;

import org.junit.Test;

import com.google.protobuf.ByteString;


public class NettyClientConnector {

	@Test
	public void test() {
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			SocketClient s = new SocketClient(group);
			ChannelFuture f = s.connect("localhost", 12345);
			f.await();
			System.out.print("future returned!");
			if(f.isDone() && !f.isSuccess()){
				TestCase.assertTrue( "Connection aborted: " + f.cause().getMessage() ,false);
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
			
		} catch(InterruptedException e ) {
			System.out.print("Connection aborted: " + e.getMessage());
			assert false;
		}
		finally {
			group.shutdownGracefully();
		}
	}
	MessageContainer generateContainer(){
		MessageContainer ret = MessageContainer.newBuilder()
			.setServiceId(1)
			.setContent(ByteString.copyFrom("blacontent".getBytes()))
			.build();
		return ret;
	}

}
