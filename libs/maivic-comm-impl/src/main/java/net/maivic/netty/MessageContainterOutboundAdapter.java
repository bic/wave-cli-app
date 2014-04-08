package net.maivic.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.maivic.comm.Maivic.MessageContainerOrBuilder;

public class MessageContainterOutboundAdapter extends
		ChannelOutboundHandlerAdapter {
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		MessageContainerOrBuilder container = (MessageContainerOrBuilder) msg;
		ChannelFuture f = ctx.writeAndFlush(container);
	}

}
