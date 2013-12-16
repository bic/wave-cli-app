package net.maivic.comm.service;

import java.util.List;

public interface NameService {
	public List<String>getValues(List<String> keys );
	public String getValue(String key);
	public void setValue(String key, String value);
	public void setValues(List<String> keys, List<String> values);
}
