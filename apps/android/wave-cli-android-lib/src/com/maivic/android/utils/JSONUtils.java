package com.maivic.android.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
	
	public static String [] getStringArrayValue(JSONObject jsonObject, String name) throws JSONException{
		return jsonArrayToStringArray(jsonObject.getJSONArray(name));
	}
	
	public static String [] jsonArrayToStringArray(JSONArray jsonArray) throws JSONException{
		String [] res = null;
		if(jsonArray != null){
			res = new String[jsonArray.length()];
			for(int i = 0; i < jsonArray.length(); i++){
				res[i] = jsonArray.getString(i);
			}
		}
		return res;
	}	
}
