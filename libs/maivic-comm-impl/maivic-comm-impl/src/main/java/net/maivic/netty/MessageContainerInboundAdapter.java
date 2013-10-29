package net.maivic.netty;

import net.maivic.comm.Maivic.MessageContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageContainerInboundAdapter extends ChannelInboundHandlerAdapter {
	public interface IncomingCallBack {
		void onIncomingMessage(MessageContainer msg);
	}
	private IncomingCallBack cb;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		MessageContainer container = (MessageContainer) msg;
		if (cb != null) {
			cb.onIncomingMessage(container);
		}
	}

	public IncomingCallBack getCb() {
		return cb;
	}

	public void setCb(IncomingCallBack cb) {
		this.cb = cb;
	}
}
