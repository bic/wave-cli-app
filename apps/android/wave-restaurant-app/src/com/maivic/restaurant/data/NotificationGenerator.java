package com.maivic.restaurant.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;


public class NotificationGenerator  {
	private static final int INFO = 0;
	private static final int WARNING = 1;
	private static final int ERROR = 2;
	public static Context ctx;
	private Activity activity;
	
	
	
	
	
	public void DisplayNotification(int notificationSeverity,
			String notificationMessage, Context ctx) {
		AlertDialog.Builder errorDialog = new AlertDialog.Builder(ctx);

		switch (notificationSeverity) {
		case INFO:
			errorDialog.setTitle("Info Message");
			errorDialog.setMessage(notificationMessage.toString());
			errorDialog.setNeutralButton("Close", null);
			errorDialog.create().show();
			break;

		case WARNING:
			errorDialog.setTitle("Warning Message");
			errorDialog.setMessage(notificationMessage.toString());
			errorDialog.setNeutralButton("Close", null);
			errorDialog.create().show();
			break;

		case ERROR:
			errorDialog.setTitle("Error Message");
			errorDialog.setMessage(notificationMessage.toString());
			errorDialog.setNeutralButton("Close", null);
			errorDialog.create().show();
			break;

		default:
			break;
		}
	}

}
