package com.maivic.android.widgets;

public class StepsParseException extends Exception{
	private static final long serialVersionUID = 8800869719034148527L;

	public StepsParseException() {
		super();
	}

	public StepsParseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public StepsParseException(String detailMessage) {
		super(detailMessage);
	}

	public StepsParseException(Throwable throwable) {
		super(throwable);
	}
}
