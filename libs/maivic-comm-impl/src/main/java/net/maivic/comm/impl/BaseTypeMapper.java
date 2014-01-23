package net.maivic.comm.impl;

import java.io.IOException;
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

import net.maivic.comm.Maivic;
import net.maivic.comm.Maivic.BaseType;
import net.maivic.comm.Maivic.BaseType.ENCODED_TYPE;
import net.maivic.comm.Maivic.SelfDescribingMessage;
import net.maivic.context.Context;

import com.google.protobuf.ByteString;
import com.google.protobuf.Message;

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
	public static Object fromBaseType(BaseType t, Type to_type) throws IOException{
		return BaseTypeMapper.getInstance()._fromBaseType(t, to_type);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object _fromBaseType(BaseType t, Type to_type) throws IOException {
		BaseType.ENCODED_TYPE type=getEncodedType(t);

		if (t.hasEncodedType() && t.getEncodedType()==ENCODED_TYPE.NONE){
			return null;
		}
		if (to_type == null ) {
			throw new IOException("Return type may not be null");
		}else if (type==null) {
			throw new IOException("No Encoded type found in BaseType argument!");
		}
		
		List ret = new ArrayList();
		boolean isList= type == ENCODED_TYPE.LIST_TYPE;
		if (isList) {
			type = getComponentType(t); 
		}
		switch(type){
			case COMPONENT_BASE_TYPE:
				for (BaseType component : t.getBaseTypeList()){
					ret.add(component);
				}
		    case MESSAGE:
				for (SelfDescribingMessage  m: t.getMessageList()){
					ret.add(this.instantiateSelfDescribingMessage(m));
				}
				break;
			case UNKNOWN:
			case NONE:
				break;
			case BYTES:
				ret.addAll(t.getBytesList());
			case STRING:
			case INT32:
			case INT64:
			case UINT32:
			case UINT64:
			case SINT32:
			case FIXED32:
			case FIXED64:
			case SFIXED32:
			case SFIXED64:
			case BOOL:
			case DOUBLE:
			case FLOAT:
			default:
			String method_name =   encoded_type_to_method.get(type);
			if (method_name == null){
				throw new IOException("unknown type " + type.toString() + "received. Cannot determine getter Method. encoded_type_to_method needs an update !!"); 
			}		
			method_name = "get" +method_name+"List";
			Method m;
			try {
				m = BaseType.class.getMethod(method_name);
				ret.addAll((List)m.invoke(t));
			} catch (NoSuchMethodException e) {
				throw new IOException("No method " + method_name + " found in " + BaseType.class.getName(), e);
			} catch (SecurityException e) {
				throw new IOException("Security Exception getting the method "+ BaseType.class.getName() + "." + method_name,e);
			} catch (IllegalAccessException e) {
				throw new IOException("Illegal access while invoking Basetype."+ BaseType.class.getName() + "." + method_name,e);
			} catch (IllegalArgumentException e) {
				throw new IOException("Illegal argument while invoking "+ BaseType.class.getName() + "." + method_name,e);
			} catch (InvocationTargetException e) {
				throw new IOException("Invocation target exception (object null?) while invoking "+ BaseType.class.getName() + "."+method_name, e);
			}
				
				break;	
		}
		if (isList)return ret;
		else if (ret.isEmpty()) return null;
		else return ret.get(0);
		
				
		
	}
	
	public static final String  MODEL_CLASS="net.maivic.protocol.Model";
	@SuppressWarnings("unchecked")
	private Object instantiateSelfDescribingMessage(SelfDescribingMessage mes) throws IOException {
		String typeName=MODEL_CLASS+"$"+mes.getTypeName();
		try {
			Class pbfObjectType = Class.forName(typeName);
			Method m;
			
			try {
				m = pbfObjectType.getMethod("parseFrom", ByteString.class);
				Object ret = m.invoke(null, mes.getContent());
				
				return ret;
			} catch (NoSuchMethodException e) {
				throw new IOException("Method " + typeName + ".parseFrom(ByteString)" + " not found!! cannot decipher " + SelfDescribingMessage.class.getName() + ", containing the type name " + mes.getTypeName());
			} catch (SecurityException e) {
				throw new IOException("Method " + typeName + ".parseFrom(ByteString)" + " not accessible!! cannot decipher " + SelfDescribingMessage.class.getName() + ", containing the type name " + mes.getTypeName());
			} catch (IllegalAccessException e) {
				throw new IOException("Cannot access " + typeName + ".parseFrom(ByteString)" + "!! cannot decipher " + SelfDescribingMessage.class.getName() + ", containing the type name " + mes.getTypeName());
			} catch (IllegalArgumentException e) {
				throw new IOException("Illegal arguments supplied to " + typeName + ".parseFrom(ByteString)" + " not found!! cannot decipher " + SelfDescribingMessage.class.getName() + ", containing the type name " + mes.getTypeName());
			} catch (InvocationTargetException e) {
				throw new IOException("Method " + typeName + ".parseFrom(ByteString)" + " cannot be invoked (is it static?)!! cannot decipher " + SelfDescribingMessage.class.getName() + ", containing the type name " + mes.getTypeName());
			} 
			
		} catch (ClassNotFoundException e) {
			throw new IOException("No Class " + typeName + "Found. Either not in model or model not loaded !!!" );
		}
		
	}
	private static Map<String,BaseType.ENCODED_TYPE> method_to_encoded_type;
	private static Map<BaseType.ENCODED_TYPE,String> encoded_type_to_method;
	
	private static void initTypeMappings() {
		if(method_to_encoded_type != null) return;
		method_to_encoded_type = new HashMap<String, BaseType.ENCODED_TYPE>();
		method_to_encoded_type.put("Bytes", BaseType.ENCODED_TYPE.BYTES);
		method_to_encoded_type.put("String", BaseType.ENCODED_TYPE.STRING);
		method_to_encoded_type.put("I32", BaseType.ENCODED_TYPE.INT32);
		method_to_encoded_type.put("I64", BaseType.ENCODED_TYPE.INT64);
		method_to_encoded_type.put("Uint32", BaseType.ENCODED_TYPE.UINT32);
		method_to_encoded_type.put("Uint64", BaseType.ENCODED_TYPE.UINT64);
		method_to_encoded_type.put("Sint32", BaseType.ENCODED_TYPE.SINT32);
		method_to_encoded_type.put("Sint64", BaseType.ENCODED_TYPE.SINT64);
		method_to_encoded_type.put("Fixed32", BaseType.ENCODED_TYPE.FIXED32);
		method_to_encoded_type.put("Fixed64", BaseType.ENCODED_TYPE.FIXED64);
		method_to_encoded_type.put("Sfixed32", BaseType.ENCODED_TYPE.SFIXED32);
		method_to_encoded_type.put("Sfixed64", BaseType.ENCODED_TYPE.SFIXED64);
		method_to_encoded_type.put("Bool", BaseType.ENCODED_TYPE.BOOL);
		method_to_encoded_type.put("Double", BaseType.ENCODED_TYPE.DOUBLE);
		method_to_encoded_type.put("Float", BaseType.ENCODED_TYPE.FLOAT);
		method_to_encoded_type.put("Message", BaseType.ENCODED_TYPE.MESSAGE);
		encoded_type_to_method = new HashMap<Maivic.BaseType.ENCODED_TYPE, String>();
		for ( Entry<String, ENCODED_TYPE> e: method_to_encoded_type.entrySet()){
			encoded_type_to_method.put(e.getValue(), e.getKey());
		}
	}
	
	private BaseType.ENCODED_TYPE getEncodedType(BaseType t) throws IOException {
		
		initTypeMappings();
		if (t.hasEncodedType()) return t.getEncodedType();
		ENCODED_TYPE ret = getEncodingFromContent(t);
		int len=0;
		String method_name = "get" + encoded_type_to_method.get(ret) + "count";
		try {
			len = (Integer) BaseType.class.getMethod(method_name).invoke(t);
		} catch (Exception e){
			throw new IOException("Could Not Access " + BaseType.class.getName() + "." + method_name + "() !!!" );
			
		}
		
		if (len < 2){
			return ret;
		} else {
			return ENCODED_TYPE.LIST_TYPE;
		}
		
	}
	private BaseType.ENCODED_TYPE getEncodingFromContent(BaseType t){
		Class<?>[] noargs = new Class<?>[]{};
		for(Entry<String, ENCODED_TYPE> e : method_to_encoded_type.entrySet()){
			try {
				if ((Integer)BaseType.class.getMethod("get"+e.getKey()+ "Count", noargs )
				.invoke(t) >0) {
					if(t.getListType()) return BaseType.ENCODED_TYPE.LIST_TYPE;
					return e.getValue();
				}
			} catch (Exception e1) {
				throw new  IllegalStateException("Could not access expected BaseType method!!",e1);
			}
		}
		return null;
	}
	private ENCODED_TYPE getComponentType(BaseType t) {
		if (t.hasComponentType()){
			return t.getComponentType();
		}
		return getEncodingFromContent(t);
		
	}
	private BaseType _toBaseType(Object o, Class<?> t) throws IOException {
		BaseType.Builder serialized = BaseType.newBuilder();
		if(o==null) {
			serialized.setEncodedType(BaseType.ENCODED_TYPE.NONE);
			
			return serialized.build();
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
					//serialized.addMessage(this._toBaseType(component, componentType));
					serialized.setListType(true);
					serialized.setComponentType(BaseType.ENCODED_TYPE.MESSAGE);
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
		}else if(Message.class.isAssignableFrom(t)){
			Message msg = (Message) o;
			SelfDescribingMessage.Builder builder = SelfDescribingMessage.newBuilder();
			builder.setContent(msg.toByteString());
			String name = msg.getClass().getName();
			builder.setTypeName(name.substring(name.lastIndexOf('$')+1));
			serialized.addMessage(builder);
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
