package com.maivic.android.app.custom;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.maivic.android.app.R;

/**
 * Dialog used to set delivery interval
 * 
 * @author Serghei
 * 
 */
public class DeliveryIntervalDialog extends Dialog {
	private ListView mListVew;
	private List<? extends Object> mData;
	private OnIntervalSelectionListener mSelectionListener;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param intervalData
	 *            list of defined intervals. Note it not be null
	 * @param selectionListener
	 *            object of {@link OnIntervalSelectionListener}
	 */
	public DeliveryIntervalDialog(Context context,
			List<? extends Object> intervalData,
			OnIntervalSelectionListener selectionListener) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.dialog_delivery_layout);

		mData = intervalData;
		mSelectionListener = selectionListener;
		mListVew = (ListView) findViewById(R.id.lstDeliveryInterval);

		mListVew.setAdapter(new DeliveryIntervalAdapter());
		mListVew.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				Object item = adapter.getItemAtPosition(position);

				if (mSelectionListener != null) {
					mSelectionListener.onIntervalSelected(
							DeliveryIntervalDialog.this, item);
				}
				dismiss();
			}
		});
	}

	/**
	 * Provide event, to get selected delivery interval
	 * 
	 * @author Serghei
	 * 
	 */
	public static interface OnIntervalSelectionListener {
		public void onIntervalSelected(DeliveryIntervalDialog dialog,
				Object selectedInterval);
	}

	public class DeliveryIntervalAdapter extends BaseAdapter {

		LayoutInflater inflater;

		public DeliveryIntervalAdapter() {
			inflater = LayoutInflater.from(getContext());
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup parent) {

			if (contentView == null) {
				contentView = inflater.inflate(
						R.layout.dialog_delivery_interval_item, null);
			}
			TextView tv;
			tv = (TextView) contentView
					.findViewById(R.id.tvDeliveryIntervalValue);
			tv.setText(getItem(position).toString());

			return contentView;
		}
	}
}
