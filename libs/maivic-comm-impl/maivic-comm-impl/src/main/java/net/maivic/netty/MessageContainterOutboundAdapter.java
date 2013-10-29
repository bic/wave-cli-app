package net.maivic.netty;

import net.maivic.comm.Maivic.MessageContainer;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class MessageContainterOutboundAdapter extends
		ChannelOutboundHandlerAdapter {
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		MessageContainer container = (MessageContainer) msg;
		ChannelFuture f = ctx.writeAndFlush(container);
	}

}
