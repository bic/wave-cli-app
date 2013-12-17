package com.maivic.android.app;

import java.util.Enumeration;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.maivic.android.app.utils.FontUtils;

public class MainActivity extends Activity {

	String [] fornts = new String[]{
			"avenir_black_oblique.ttf", 
			"avenir_black.ttf",
			"avenir_book_oblique.ttf",
			"avenir_book.ttf",
			"avenir_heavy_oblique.ttf",
			"avenir_heavy.ttf",
			"avenir_light_oblique.ttf",
			"avenir_light.ttf",
			"avenir_medium_oblique.ttf",
			"avenir_oblique.ttf",
			"avenir_roman.ttf",
	};
	
	int currentFont = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Properties propers = System.getProperties();
		
		findViewById(R.id.btnButton1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, OffersActivity.class);
				startActivity(intent);
			}
		});
		
		findViewById(R.id.btnButton2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				TextView tv = (TextView) findViewById(R.id.tvHelloWorld);
				String font = fornts[currentFont];
				FontUtils.setFontFotTextView(MainActivity.this, tv, font);
				
				tv.setText("Current font: " + font + " " + currentFont
						+ " of " + fornts.length);
				currentFont = (currentFont + 1) % fornts.length;
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
