package net.maivic.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.context.Context;
import net.maivic.context.Log;

public class MessageContainerInboundAdapter extends ChannelInboundHandlerAdapter {
	private final Log log = Context.get().log();
	private static final String TAG = MessageContainerInboundAdapter.class.getName(); 
	public interface IncomingCallBack {
		void onIncomingMessage(MessageContainer msg);
	}
	private IncomingCallBack cb;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		MessageContainer container = (MessageContainer) msg;
		log.e(TAG, "Incoming message_container: "+ container.toString());
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
