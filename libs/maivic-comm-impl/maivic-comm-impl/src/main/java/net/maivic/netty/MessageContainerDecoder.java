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
			buf.markReaderIndex();
			ByteBufInputStream bufStream = new ByteBufInputStream(buf);
			if(buffer_ignore_bytes.containsKey(buf)){
				int len = buffer_ignore_bytes.get(buf);
				int now  = buf.readableBytes();
				int skip = Math.min(now, len);
				buf.readerIndex(now+skip);
				if(len == skip){
					this.buffer_ignore_bytes.remove(buf);
				} else {
					this.buffer_ignore_bytes.put(buf, len-skip);
				}
			}
			try{
				int len =-1;
				if (buffer_to_length.containsKey(buf)){
					len = buffer_to_length.get(buf);
				} else {
					int firstByte= bufStream.read();
					len = CodedInputStream.readRawVarint32(firstByte, new ByteBufInputStream(buf));
					buf.markReaderIndex();
					
					int ret = buf.ensureWritable(Math.max(len-buf.readableBytes(), 0),false);
					if (ret == 3) {
						// Cannot decode as the bytebuffer cannot grow to the expected message size
						buffer_to_length.remove(buf);
						int skip= buf.readableBytes();
						len = len - skip;
						buf.readerIndex(buf.readerIndex()+skip);
						buffer_ignore_bytes.put(buf, len);
					}
				}
				
				if( buf.readableBytes()>=len){
					out.add(MessageContainer.parseFrom(bufStream));
					buffer_to_length.remove(buf); // Does not throw if buf is not in map
				}else if (!buffer_to_length.containsKey(buf)){
					buffer_to_length.put(buf, len);
					
				} else {
					// Do nothing. We know what to expect but have still not got the nessesary length
				}
			} catch (InvalidProtocolBufferException e){
				throw e;
			} catch (IOException e) {
				buf.resetReaderIndex();
			} finally {
				
			}

	}

}
