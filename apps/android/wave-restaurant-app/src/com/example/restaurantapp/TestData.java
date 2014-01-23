package com.example.restaurantapp;

import java.util.ArrayList;
import java.util.List;

import com.example.restaurantapp.TabContent.Course;

public class TestData {

	public static TabContent insertCourses(TabContent tabContent){

		TabContent ret = tabContent;
		if (ret == null){
			ret = new TabContent();
			ret.setTitle("Propunere");
		}

		for (int i=0;i<3;i++){
			Course c = new Course();
			switch(i){
			case 0:
				c.setTitle("Supă de pui");
				c.setCourse_type("Felul 1");
				c.setConfigurable(true);
				break;
			case 1:
				c.setTitle("Friptură de pui si patrunjel crud");
				c.setCourse_type("Fel Principal");
				c.setConfigurable(true);
				break;
			case 2:
				c.setTitle("Desert");
				c.setCourse_type("Desert");
				c.setConfigurable(false);
				break;	
			}
			ret.courses.add(c);

		}
		return ret;
	}

	public static List<TabContent> generateTabContentList(List<TabContent> tabContentList){
		List<TabContent> ret = tabContentList;
		if (ret == null){
			ret = new ArrayList<TabContent>();

		}
		for (int i=0;i<3;i++){
			TabContent tabcontent = new TabContent();

			ret.add(tabcontent);
			insertCourses(tabcontent);
			switch(i){
			case 0:
				tabcontent.setTitle("Supă de pui");

				break;
			case 1:
				tabcontent.setTitle("Friptură de pui");

				break;
			case 2:
				tabcontent.setTitle("Desert");

				break;
			}
		}

		return ret;
	}
}



