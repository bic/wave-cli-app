package com.maivic.android.app;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.maivic.android.app.adapter.CommandCourseAdapter;
import com.maivic.android.app.adapter.CommandOptionsAdapter;
import com.maivic.android.app.adapter.CommandWrapperAdapter;

public class CommandConfirmationActivty extends BaseActivity {
	
	private ListView mCommandsListView;
	private BillLayout mBillLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCommandsListView = (ListView) findViewById(R.id.listView1);
//		mCommandsListView.setAdapter(new CommandCourseAdapter(this));
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View billView = inflater.inflate(R.layout.command_bill_item, null);
		mCommandsListView.addFooterView(billView);
		
		mBillLayout = new BillLayout(billView);
		
		List<BaseAdapter> adapters = Arrays
				.asList(new BaseAdapter[] { new CommandCourseAdapter(this),
						new CommandOptionsAdapter(this),
						/*new CommandBillAdapter(this) */});
		
		CommandWrapperAdapter adapter = new CommandWrapperAdapter(adapters);
		mCommandsListView.setAdapter(adapter);
		
		findViewById(R.id.btnOtherOffer).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mBillLayout.showConfirmationViewWithSupplement();
			}
		});
	}

	@Override
	protected View getContentView() {
		return mInflater.inflate(R.layout.activity_command_confirmation, null);
	}

	class BillLayout{
		private View mLayout;
		View paymentButtonsSection;
		WebView paymentNotaWebView; 
		View totalSection;
		View supplementSection;
		View paymentDescSection;
		View alreadyPayedSection;
		
		public BillLayout(View layout){
			mLayout = layout;
			
			paymentButtonsSection = layout.findViewById(R.id.paymentButtonsSection);
			paymentNotaWebView = (WebView) layout.findViewById(R.id.wvNotaDePlata);
			totalSection = layout.findViewById(R.id.totalPaimentSection);
			supplementSection =  layout.findViewById(R.id.extraPaidSection);
			paymentDescSection = layout.findViewById(R.id.paymentDescSection);
			alreadyPayedSection = layout.findViewById(R.id.alreadyPaidSection);
			
			findViewById(R.id.btnPayCard).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showConfirmationViewWithoutSupplement();
				}
			});
			
			findViewById(R.id.btnPayCash).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showConfirmationTotalView();
				}
			});
		}
		
		public void showConfirmationViewWithSupplement(){
			paymentButtonsSection.setVisibility(View.VISIBLE);
			supplementSection.setVisibility(View.VISIBLE);
			totalSection.setVisibility(View.GONE);
			paymentDescSection.setVisibility(View.GONE);
			alreadyPayedSection.setVisibility(View.VISIBLE);
		}
		
		public void showConfirmationViewWithoutSupplement(){
			paymentButtonsSection.setVisibility(View.GONE);
			supplementSection.setVisibility(View.GONE);
			totalSection.setVisibility(View.GONE);
			paymentDescSection.setVisibility(View.VISIBLE);
			alreadyPayedSection.setVisibility(View.VISIBLE);
		}
		
		public void showConfirmationTotalView(){
			paymentButtonsSection.setVisibility(View.VISIBLE);
			supplementSection.setVisibility(View.GONE);
			totalSection.setVisibility(View.VISIBLE);
			paymentDescSection.setVisibility(View.GONE);
			alreadyPayedSection.setVisibility(View.GONE);
		}
		
	}

}
