package net.maivic.comm.impl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FileDescriptor;

import net.maivic.comm.Maivic.BaseType;
import net.maivic.comm.Maivic.BaseType.ENCODED_TYPE;
import net.maivic.context.Context;

public class BaseTypeMapper {
	private static BaseTypeMapper inst = new BaseTypeMapper();
	private static BaseTypeMapper instance;
	private static BaseTypeMapper getInstance() {
		if(instance == null ) {
			instance = (BaseTypeMapper)Context.get().get(BaseTypeMapper.class.getName());
		}
		return instance;
	}
	public static BaseType toBaseType(Object o, Class<?> o_type) throws IOException{
		return BaseTypeMapper.getInstance()._toBaseType(o, o_type);
	}
	public static Object fromBaseType(BaseType t, Class<?>to_type) throws IOException{
		return BaseTypeMapper.getInstance()._fromBaseType(t, to_type);
	}
	private Object _fromBaseType(BaseType t, Class<?> to_type) throws IOException {
		BaseType.ENCODED_TYPE type=getEncodedType(t);
		if (t.hasEncodedType() && t.getEncodedType()==ENCODED_TYPE.NONE){
			return null;
		}
		if (to_type == null ) {
			throw new IOException("Return type may not be null");
		}else if (type==null) {
			throw new IOException("No Encoded type found in BaseType argument!");
		}
		List<?> ret = null;
		
		
		boolean found=false;
		for (Entry<String, ENCODED_TYPE> enc: mapping.entrySet()){
			if(enc.getValue() ==type) {
				for (Method m : t.getClass().getMethods()) {
					if(m.getName() == "get" + enc.getKey() + "List" ){
						try {
							ret= (List<?>)m.invoke(t);
							found=true;
							break;
						} catch (Exception e) {
							throw new IOException("Could not read from Basetype.get" +enc.getKey() + "List()" );
						}
						
					}
						
				}
				if(found) break;
				
			}
		}
		if (found) {
			if(to_type.isArray()) {
				Object array_ret = Array.newInstance(to_type, ret.size());
				for( int i =0; i<ret.size(); i++  ){
					Array.set(array_ret, i, ret.get(i));
				}
				return array_ret;
			} else if (to_type.isAssignableFrom(List.class)) {
				return ret;
			} else {
				return ret.get(0);
			}
		} else if (t.getListType() && t.hasComponentType() && t.getComponentType() == ENCODED_TYPE.UNKNOWN){ 
			//Empty List, so cannot deduce contained type
			ret= new ArrayList();
		}
		return ret;
				
		
	}
	private static Map<String,BaseType.ENCODED_TYPE> mapping;
	private static void initTypeMappings() {
		if(mapping != null) return;
		mapping = new HashMap<String, BaseType.ENCODED_TYPE>();
		mapping.put("Bytes", BaseType.ENCODED_TYPE.BYTES);
		mapping.put("String", BaseType.ENCODED_TYPE.STRING);
		mapping.put("I32", BaseType.ENCODED_TYPE.INT32);
		mapping.put("I64", BaseType.ENCODED_TYPE.INT64);
		mapping.put("Uint32", BaseType.ENCODED_TYPE.UINT32);
		mapping.put("Uint64", BaseType.ENCODED_TYPE.UINT64);
		mapping.put("Sint32", BaseType.ENCODED_TYPE.SINT32);
		mapping.put("Sint64", BaseType.ENCODED_TYPE.SINT64);
		mapping.put("Fixed32", BaseType.ENCODED_TYPE.FIXED32);
		mapping.put("Fixed64", BaseType.ENCODED_TYPE.FIXED64);
		mapping.put("Sfixed32", BaseType.ENCODED_TYPE.SFIXED32);
		mapping.put("Sfixed64", BaseType.ENCODED_TYPE.SFIXED64);
		mapping.put("Bool", BaseType.ENCODED_TYPE.BOOL);
		mapping.put("Double", BaseType.ENCODED_TYPE.DOUBLE);
		mapping.put("Float", BaseType.ENCODED_TYPE.FLOAT);
		mapping.put("ContainedObject", BaseType.ENCODED_TYPE.CONTAINED_OBJECT);
				
	}
	private BaseType.ENCODED_TYPE getEncodedType(BaseType t) {
		
		initTypeMappings();
		if (t.hasEncodedType()) return t.getEncodedType();
		Class<?>[] noargs = new Class<?>[]{};
		for(Entry<String, ENCODED_TYPE> e : mapping.entrySet()){
			try {
				if ((Integer)BaseType.class.getMethod("get"+e.getKey()+ "Count", noargs )
				.invoke(t) >0) {
					if(t.getListType())
					return e.getValue();
				}
			} catch (Exception e1) {
				throw new  InternalError("Could not access expected BaseType method!!",e1);
			}
		}
		return null;
	}
	private BaseType _toBaseType(Object o, Class<?> t) throws IOException {
		BaseType.Builder serialized = BaseType.newBuilder();
		if(o==null) {
			return null;
		}
		if (t == null) {
			t= o.getClass();
		}
		if (isIterable(t)) {
			Iterator iter = null;
			if (t.isArray()) {
				iter=Arrays.asList(o).iterator();
			} else {
				iter = Iterable.class.cast(o).iterator();
			}
			Class<?> componentType= t.getComponentType();
			while( iter.hasNext()){
				if (componentType==null && o!=null) {
					componentType = o.getClass();
				}
				Object component= iter.next(); 
				if(component!= null && !componentType.isAssignableFrom(component.getClass())){
					throw new IOException("Cannot serialize object if type " + 
							component.getClass().getName() +
							" to a list containing objects of type: " + 
							componentType.getName());
				}
				if(isIterable(componentType)) {
					serialized.addContainedObject(this._toBaseType(component, componentType));
					serialized.setListType(true);
					serialized.setComponentType(BaseType.ENCODED_TYPE.CONTAINED_OBJECT);
				} else {
					serialized.mergeFrom(this._toBaseType(component, componentType));
				}
			}
		}else if( Integer.class.isAssignableFrom( t) || int.class.isAssignableFrom(t)) {
			serialized.addI32((Integer)o);
		}else if(Boolean.class.isAssignableFrom(t)|| boolean.class.isAssignableFrom(t)){
			serialized.addBool((Boolean) o);
		}else if(String.class.isAssignableFrom(t)){
			serialized.addString((String) o);
		}else if(byte[].class.isAssignableFrom(t) || byte[].class.isAssignableFrom(t)){
			serialized.addBytes(ByteString.copyFrom((byte[]) o));
		}else if(Double.class.isAssignableFrom(t) || double.class.isAssignableFrom(t)){
			serialized.addDouble((Double) o );
		}else {
			throw new IllegalArgumentException("Cannot use type " + t.getName() + "in RPC");		
		}
		return serialized.build();
		
	}
	private boolean isIterable(Class<?> t) {
		return t.isArray() || Iterable.class.isAssignableFrom(t)
			&&!String.class.isAssignableFrom(t)
			&&!byte[].class.isAssignableFrom(t);
	}
}
