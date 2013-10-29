package net.maivic.netty;

import java.io.OutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.maivic.comm.Maivic.MessageContainer;

public class MessageContainerEncoder extends
		MessageToByteEncoder<MessageContainer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageContainer msg,
			ByteBuf buf) throws Exception {
		OutputStream out = new ByteBufOutputStream(buf);
		msg.writeDelimitedTo(out);
	}

}
