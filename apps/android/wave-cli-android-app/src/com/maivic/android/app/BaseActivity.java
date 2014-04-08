package com.maivic.android.app;

import net.maivic.comm.RPC.OfferQuery;
import net.maivic.protocol.Model.Menu;
import net.maivic.protocol.Model.Offer;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class BaseActivity extends FragmentActivity {
	protected LinearLayout containerView;
	protected LayoutInflater mInflater;
	
	private ProgressDialog mProgressDlg;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_base);
		containerView = (LinearLayout) findViewById(R.id.container);
		mInflater = LayoutInflater.from(this);

		View content = getContentView();
		if (content != null) {
			LinearLayout.LayoutParams params = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			containerView.addView(content, params);
		}

		mProgressDlg = new ProgressDialog(this);
		mProgressDlg.setCancelable(false);
	}
	
	protected View getContentView() {
		return null;
	}
	
	public void showProgressDlg(){
		mProgressDlg.show();
//		if(!mProgressDlg.isShowing()){
//			mProgressDlg.show();
//		}
	}
	
	public void hideProgressDlg(){
//		if(mProgressDlg.isShowing()){
//			mProgressDlg.dismiss();
//		}
		
		mProgressDlg.dismiss();
	}
}
