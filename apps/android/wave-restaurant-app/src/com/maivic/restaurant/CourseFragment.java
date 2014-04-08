package com.maivic.restaurant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.protocol.Model;
import net.maivic.protocol.Model.LocationBit;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.relations.OfferRelations;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restaurantapp.R;

import com.maivic.restaurant.data.LocalOfferOptionDistributor;
import com.maivic.restaurant.data.OfferContent;
import com.maivic.restaurant.data.OfferOptionsContent;
import com.maivic.restaurant.data.UpdateDataCallback;

public class CourseFragment extends Fragment {
	protected static final String mTag = null;
	private LocalOfferOptionDistributor course;
	private String title;
	private boolean enabled;

	private List<OfferOption> myoff;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	TextView course_name = null;
	EditText course_name_editor = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Bundle args = getArguments();

		// TextView e =
		// (TextView)this.getActivity().findViewById(R.id.course_name_edit);

		View v = inflater.inflate(R.layout.course_layout, container);
		// View tv = v.findViewById(R.id.set_delivery_hour);
		// View course_name = v.findViewById(R.id.course_name_edit);
		course_name = (TextView) v.findViewById(R.id.course_name_edit);
		course_name_editor = (EditText) v.findViewById(R.id.course_name_edit2);

		TextView course_type = (TextView) v.findViewById(R.id.course_type);
		course_type.setText(this.title);

		/*
		 * final CheckBox cb = (CheckBox)v.findViewById(R.id.configure);
		 * cb.setChecked(this.course.isConfigurable());
		 * cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView,
		 * boolean isChecked) {
		 * CourseFragment.this.course.setConfigurable(isChecked); } });
		 * 
		 * cb.setEnabled(this.enabled);
		 */

		ImageButton delete_course = (ImageButton) v
				.findViewById(R.id.delete_course);
		delete_course.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((TextView) course_name).setText("Neconfigurat");
				course_name_editor.setText("Neconfigurat");
				// cb.setChecked(false);
				// cb.setEnabled(false);
				course_name.setFocusable(false);
				course_name.setFocusableInTouchMode(false);
				course_name.setOnTouchListener(null);
				course_name_editor.setVisibility(View.GONE);

			}
		});

		course_name_editor.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// cb.setEnabled(true);
				// cb.setChecked(this.course.isConfigurable());
				course_name.setText(s);

			}
		});

		course_name_editor.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					course_name_editor.clearFocus();
				}
				return false;

			}
		});

		course_name.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				course_name.setVisibility(View.GONE);
				course_name_editor.setVisibility(View.VISIBLE);
				// course_name_editor.setPressed(true);
				course_name_editor.requestFocus();
				return false;
			}
		});
		course_name_editor
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {

							course_name.setVisibility(View.VISIBLE);
							course_name_editor.setVisibility(View.INVISIBLE);

						} else {
							// course_name_editor.setSelectAllOnFocus(true);

							// getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
							course_name_editor.setSelection(course_name_editor
									.getText().length());
							InputMethodManager imm = (InputMethodManager) getActivity()
									.getSystemService(
											Context.INPUT_METHOD_SERVICE);
							imm.showSoftInput(course_name_editor,
									InputMethodManager.SHOW_IMPLICIT);

						}

					}
				});

		return v;

	}

	Map<Long, OfferOption> offer_options_by_id = new HashMap<Long, OfferOption>();
	private OfferActivity activity;

	private int myCourse;

	@Override
	public void onInflate(Activity activity, AttributeSet attrs,
			Bundle savedInstanceState) {

		super.onInflate(activity, attrs, savedInstanceState);
		int[] attrsArray = { android.R.attr.id, R.attr.title,
				R.attr.configurable };
		TypedArray ta = activity.obtainStyledAttributes(attrs, attrsArray);
		myCourse = ta.getResourceId(0, View.NO_ID);
		title = ta.getString(1);
		enabled = ta.getBoolean(2, true);
		Resources res = ta.getResources();
		OfferActivity a = (OfferActivity) activity;
		this.activity = a;
		int idx = myIndex(myCourse);
		LocalOfferOptionDistributor source = a.courseContent[idx];
		source.registerValueListener(new UpdateDataCallback<Model.OfferOption>() {
			
			@Override
			public void updateValues(OfferOption value) {
				setCourseArgs(value);
			}
		});
		
		
	}

	private int myIndex(int R_id) {

		switch (R_id) {
		case R.id.first_course_fragment:
			return 0;
		case R.id.main_course_fragment:
			return 1;
		case R.id.third_course_fragment:
			return 2;

		default:
			return -1;
		}
	
	}

	private void setCourseArgs(OfferOption courses) {

		((TextView) course_name).setText(courses.getNameSingular());
		((TextView) course_name_editor).setText(courses.getNameSingular());

	}

}
