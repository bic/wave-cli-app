package com.example.restaurantapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.view.View;

public class TabContent {
	public interface DataCallback<T>{
		

		public void updated(T value);
	}
	public static class Course implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String title;
		private String course_type;

		private boolean configurable;

		public boolean isConfigurable() {
			return configurable;
		}
		List<DataCallback<Boolean>>configurableListeners = new ArrayList<DataCallback<Boolean>>();
		public void addConfigurableListener(DataCallback<Boolean> c){
			this.configurableListeners.add(c);
		}
		public void setConfigurable(boolean configurable) {
			this.configurable = configurable;
			for (DataCallback<Boolean>  c: configurableListeners){
				c.updated(this.configurable);
			}
		}

		public String getCourse_type() {
			return course_type;
		}

		public void setCourse_type(String course_type) {
			this.course_type = course_type;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
	private String mTitle = null;
	public List<Course> courses = new ArrayList<Course>();

	public TabContent(TabContent tabContent) {
		// TODO Auto-generated constructor stub
		mTitle = tabContent.getTitle();
		
	}
	public TabContent(){
		
	}

	public TabContent(String title) {
		// TODO Auto-generated constructor stub
		mTitle = title;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	
}
