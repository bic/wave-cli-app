package net.maivic.context;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	Map<String, Logger> loggers = new HashMap<String, Logger>();
	private int writeException(char level,String tag,String message, Throwable t){
		Level java_log_level = Level.OFF;
		switch(level)
		{
			
			case 'd':
				java_log_level = Level.FINE;
				break;
			case 'i':
				java_log_level = Level.INFO;
				break;
			case 'e':
				java_log_level = Level.SEVERE;
				break;
			case 'w':
				java_log_level = Level.WARNING;
				break;
			case 'W':
			default:
				java_log_level= Level.parse("1200");
		}
		Logger logger=Logger.getLogger(tag);
		String mess = message;
		if (t!=null){
			logger.log(java_log_level, message,t);
		}else{
			logger.log(java_log_level, message);
		}
		return message.length();
	}
	public  int i(String tag, String msg, Throwable t){
		return writeException('i', tag, msg, t);
	}
	public  int i(String tag, String msg){
		return writeException('i', tag, msg, null);
	}
	public  int d(String tag, String msg, Throwable t){
		return writeException('d', tag, msg, t);
	}
	public  int d(String tag, String msg){
		return writeException('d', tag, msg, null);
	}
	public  int e(String tag, String msg, Throwable t){
		return writeException('e', tag, msg, t);
	}
	public  int e(String tag, String msg){
		return writeException('e', tag, msg, null);
	}
	public  int w(String tag, String msg, Throwable t){
		return writeException('w', tag, msg, t);
	}
	public  int w(String tag, String msg){
		return writeException('w', tag, msg, null);
	}
	public  int wtf(String tag, String msg, Throwable t){
		return writeException('W', tag, msg, t);
	}
	public  int wtf(String tag, String msg){
		return writeException('W', tag, msg, null);
	}
}
