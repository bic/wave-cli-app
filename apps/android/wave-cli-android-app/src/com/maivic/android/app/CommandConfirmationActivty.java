package com.maivic.android.app;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.maivic.android.app.adapter.CommandCourseAdapter;
import com.maivic.android.app.adapter.CommandOptionsAdapter;
import com.maivic.android.app.adapter.CommandWrapperAdapter;
import com.maivic.android.app.api.APIHelper;

public class CommandConfirmationActivty extends BaseActivity {
	public static final String EXTRA_OFFER = "extra_offer";
	
	private ListView mCommandsListView;
	private BillLayout mBillLayout;
	private Offer mOffer; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Toast.makeText(this, "CommandConfirmationActivity...", Toast.LENGTH_SHORT).show();
		
		mCommandsListView = (ListView) findViewById(R.id.listView1);
//		mCommandsListView.setAdapter(new CommandCourseAdapter(this));
		
		View billView = mInflater.inflate(R.layout.command_bill_item, null);
		
		View headerView = mInflater.inflate(R.layout.view_other_offert, null);
		
		mCommandsListView.addHeaderView(headerView);
		mCommandsListView.addFooterView(billView);
		
		mBillLayout = new BillLayout(billView);
		
		List<BaseAdapter> adapters = Arrays.asList(new BaseAdapter[] {
				new CommandCourseAdapter(this),
				new CommandOptionsAdapter(this), });
		
		CommandWrapperAdapter adapter = new CommandWrapperAdapter(adapters);
		mCommandsListView.setAdapter(adapter);
		
		findViewById(R.id.btnOtherOffer).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mBillLayout.showConfirmationViewWithSupplement();
			}
		});
		
		OfferOption offerOption;
		
		getExtras();
		loadCommands();
	}
	
	private void getExtras(){
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
			mOffer = (Offer) extras.get(EXTRA_OFFER);
		}
		
	}
	
	private void loadCommands(){
		new AsyncTask<Void, Void, List<OfferOption>>() {
			
			@Override
			protected List<OfferOption> doInBackground(Void... params) {
				
				List<OfferOption> options = null;
				try {
					APIHelper apiHelper = APIHelper.getInstance();
					options = apiHelper.getOfferOptions(mOffer);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
				return options;
			}

			@Override
			protected void onPostExecute(List<OfferOption> result) {
				hideProgressDlg();
				
				Toast.makeText(CommandConfirmationActivty.this, "onPostExecute", Toast.LENGTH_SHORT).show();
			}

			@Override
			protected void onPreExecute() {
				showProgressDlg();
				
				Toast.makeText(CommandConfirmationActivty.this, "onPreExecute",
						Toast.LENGTH_SHORT).show();
			}
			
		}.execute();
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
