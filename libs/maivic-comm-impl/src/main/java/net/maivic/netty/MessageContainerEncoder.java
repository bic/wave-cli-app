package net.maivic.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.OutputStream;

import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.comm.Maivic.MessageContainerOrBuilder;

public class MessageContainerEncoder extends
		MessageToByteEncoder<MessageContainerOrBuilder> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageContainerOrBuilder msg,
			ByteBuf buf) throws Exception {
		OutputStream out = new ByteBufOutputStream(buf);
		MessageContainer send_cont;
	 	if (msg instanceof MessageContainer.Builder) {
			send_cont = ((MessageContainer.Builder) msg).build();
		} else {
			send_cont=(MessageContainer) msg;
		}
	
		send_cont.writeDelimitedTo(out);
	}

}
