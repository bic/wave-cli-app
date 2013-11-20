package com.maivic.android.app.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * This widget used to indicate active commands
 * 
 * @author Serghei
 */
public class BallTextView extends TextView{

	private Paint mPaint;
	
	public BallTextView(Context context) {
		super(context);
		initilizeInternal();
	}

	public BallTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initilizeInternal();
	}

	public BallTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initilizeInternal();
	}
	
	private void initilizeInternal(){
		setGravity(Gravity.CENTER);
	}

	@Override
	protected void onDraw(Canvas canvas) {		
		
		if(mPaint == null){
			// Lazy create/initialize paint object
			mPaint = new Paint();
			mPaint.setColor(0xFFF04E23);
		}
		
		int height = getMeasuredHeight();
		int width = getMeasuredWidth();
		
		// make square size
		if(height != width){
			if(height > width){
				setWidth(height);
			} else{
				setHeight(width);
			}
		}
		
		int size = Math.max(height, width);
	
		float cx = size / 2;
		float cy = size / 2;
		float radius = size / 2;
		canvas.drawCircle(cx, cy, radius, mPaint);
		
		super.onDraw(canvas);
	}
}
