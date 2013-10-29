package net.maivic.comm;

import java.util.Iterator;

/**
 * The earliest time to send the message using the last transport yielded by {@link #next()}
 * The return value may me 0 or any x < {@link System#currentTimeMillis()}
 * @return milliseconds since 1970
 */

public interface  SendStrategy<T> extends Iterator<Long> {
	Transport<T> getTransport();

}
