package net.maivic.comm.exception;

public class SendFailed extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SendFailed(String message, Throwable cause) {
		super(message,cause);
	}
	public SendFailed(Throwable cause) {
		super(cause);
	}

}
