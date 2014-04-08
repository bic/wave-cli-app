package com.maivic.restaurant;

import net.maivic.context.Log;
import net.maivic.netty.NettyTransportImpl;

public class AndroidLogger extends Log{
	android.util.Log fwLogger;
	
	
	@Override
	public int i(String tag, String msg) {
		// TODO Auto-generated method stub
		
		return fwLogger.i(tag, msg);
	}
	
	@Override
	public int d(String tag, String msg, Throwable t) {
		// TODO Auto-generated method stub
		return fwLogger.d(tag, msg, t);
	}

	@Override
	public int d(String tag, String msg) {
		// TODO Auto-generated method stub
		return fwLogger.d(tag, msg);
	}

	@Override
	public int e(String tag, String msg, Throwable t) {
		// TODO Auto-generated method stub
		return fwLogger.e(tag, msg, t);
	}

	@Override
	public int e(String tag, String msg) {
		// TODO Auto-generated method stub
		return fwLogger.e(tag, msg);
	}

	@Override
	public int w(String tag, String msg, Throwable t) {
		// TODO Auto-generated method stub
		return fwLogger.w(tag, msg, t);
	}

	@Override
	public int wtf(String tag, String msg, Throwable t) {
		// TODO Auto-generated method stub
		return fwLogger.wtf(tag, msg, t);
	}

	@Override
	public int wtf(String tag, String msg) {
		// TODO Auto-generated method stub
		return fwLogger.wtf(tag, msg);
	}

	@Override
	public int i(String tag, String msg, Throwable t) {
		// TODO Auto-generated method stub
		return fwLogger.i(tag, msg, t);
	}
	@Override
	public int w(String tag, String msg) {
		// TODO Auto-generated method stub
		return fwLogger.w(tag, msg);
	}
	
	

}
