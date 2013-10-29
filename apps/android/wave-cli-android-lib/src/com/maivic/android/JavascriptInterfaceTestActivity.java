package com.maivic.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maivic.android.webviewbridge.jsinterface.JavaScriptContactsInterface;
import com.maivic.android.webviewbridge.jsinterface.JavaScriptContactsInterface.ActivityResultBinder;
import com.maivic.android.webviewbridge.jsinterface.JavaScriptContactsInterface.ActivityResultDelegate;
import com.maivic.android.webviewbridge.jsinterface.JavaScriptSMSInterface;

@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class JavascriptInterfaceTestActivity extends Activity implements ActivityResultBinder{
	
	// WebView mWebView;
	//
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	//
	// mWebView = new WebView(this);
	// mWebView.getSettings().setJavaScriptEnabled(true);
	// mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
	// setContentView(mWebView);
	//
	//
	// mWebView.setWebViewClient(new WebViewClient(){
	//
	// @Override
	// public void onReceivedError(WebView view, int errorCode,
	// String description, String failingUrl) {
	// super.onReceivedError(view, errorCode, description, failingUrl);
	// System.err.println("onReceivedError "+errorCode+" "+description+" "+failingUrl);
	// }
	//
	// });
	//
	// mWebView.setWebChromeClient(new WebChromeClient(){
	//
	//
	// @Override
	// public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
	// System.err.println("onConsoleMessage");
	// return super.onConsoleMessage(consoleMessage);
	// }
	//
	// @Override
	// public boolean onJsAlert(WebView view, String url, String message,
	// JsResult result) {
	// System.err.println("onJsAlert");
	// return super.onJsAlert(view, url, message, result);
	// }
	//
	// });
	//
	//
	// mWebView.loadDataWithBaseURL("'about:blank'",
	// "<html><head><script type=\"text/javascript\">function testFunction(){ alert('Hello! de la javascript'); }</script></head> <title>TEst</title> <body><h1>Hello World!!!</h1></body></html>",
	// "txt/html", "utf-8", null);
	// // mWebView.loadData("Hello World!!!", "txt/html", "utf-8");
	//
	// }
	//
	// @Override
	// protected void onResume() {
	// super.onResume();
	// mWebView.loadUrl("javascript:testFunction()");
	//
	// }

	WebView myBrowser;
	EditText Msg;
	Button btnSendMsg;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		JSONArray array = new JSONArray();
//		
//		array.put(JSONObject.NULL);
//		try {
//			array.put((new JSONObject().put("json", new JSONObject())
//					.put("boolean", true).put("int", 1)
//					.putOpt("nullValue", null).put("object", new Test(
//					"testClass"))));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		
//		System.err.println("The json array with null "+array.toString());
//		
//		
//		System.err.println("The onCreate threadId: "+Thread.currentThread().getId());
		
		
		setContentView(com.maivic.android.widgets.R.layout.activity_javascript_test);
		// setContentView();
		myBrowser = (WebView) findViewById(com.maivic.android.widgets.R.id.mybrowser);

		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
				this);
		myBrowser.addJavascriptInterface(myJavaScriptInterface,
				"AndroidFunction");
		
		JavaScriptContactsInterface contactsInterface = new JavaScriptContactsInterface();
		contactsInterface.setActivityContext(this);
		myBrowser.addJavascriptInterface(contactsInterface,
				"Contacts");
		
		JavaScriptSMSInterface smsInterface = new JavaScriptSMSInterface();
		smsInterface.setActivityContext(this);
		myBrowser.addJavascriptInterface(smsInterface,
				"SMS");
		

		myBrowser.getSettings().setJavaScriptEnabled(true);
		
//		String htmlColntent = loadAssest(this, "html_file.html");
		
//		myBrowser.loadData(htmlColntent, "html/txt", "UTF-8");
		
		myBrowser.loadUrl("file:///android_asset/html_file.html");

		Msg = (EditText) findViewById(com.maivic.android.widgets.R.id.msg);
		btnSendMsg = (Button) findViewById(com.maivic.android.widgets.R.id.sendmsg);
		btnSendMsg.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String msgToSend = Msg.getText().toString();
				myBrowser.loadUrl("javascript:callFromActivity(\"" + msgToSend
						+ "\")");

			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (mResultDelegate != null
				&& mResultDelegate.onActivityResult(requestCode, resultCode, data)) {
			mResultDelegate = null;
			return;
		}
	}

	public class MyJavaScriptInterface {
		Context mContext;

		MyJavaScriptInterface(Context c) {
			mContext = c;
		}

		@JavascriptInterface
		public void showToast(String toast) {
			Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
		}

		@JavascriptInterface
		public void openAndroidDialog(String toast) {
			
			System.err.println("The javascript threadId: "+Thread.currentThread().getId());
			
			AlertDialog.Builder myDialog = new AlertDialog.Builder(
					JavascriptInterfaceTestActivity.this);
			myDialog.setTitle("JavasctiptResponse");
			myDialog.setMessage(toast);
			myDialog.setPositiveButton("Close", null);
			myDialog.show();
		}

	}

	public static String streamToString(InputStream in){
		
		StringBuilder bs = new StringBuilder();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line = null;
		try {
			while((line = reader.readLine()) != null){
				bs.append(line);
				bs.append("\n");
			}
			reader.close();
		} catch (IOException e) {
		}
		
		return bs.toString();
	}
	
	public static String loadAssest(Context context, String assestName){
		try {
			InputStream in = context.getAssets().open(assestName);
			return streamToString(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public void registerActivityResultDeligate(ActivityResultDelegate delegate) {
		this.mResultDelegate = delegate;
	}
	
	ActivityResultDelegate mResultDelegate;
}
// }
