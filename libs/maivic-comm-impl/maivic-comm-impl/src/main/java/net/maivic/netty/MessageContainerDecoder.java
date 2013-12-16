package net.maivic.netty;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.maivic.comm.Maivic.MessageContainer;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageContainerDecoder extends ByteToMessageDecoder {
	Map<ByteBuf, Integer> buffer_to_length = new HashMap<ByteBuf, Integer>();
	Map<ByteBuf, Integer> buffer_ignore_bytes = new HashMap<ByteBuf, Integer>();
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws Exception {
				MessageContainer container = MessageContainer.parseFrom(new ByteBufInputStream(buf));
				out.add(container);
				
		}

}
