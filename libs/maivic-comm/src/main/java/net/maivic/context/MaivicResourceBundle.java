package net.maivic.context;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MaivicResourceBundle extends ResourceBundle {
	Hashtable<String,String> defaultConstructibleImplementations = new Hashtable<String,String>();
	Hashtable<String,String> classRefernces = new Hashtable<String,String>();
	Hashtable<String, Object> constantValues=new Hashtable<String, Object>();
	public MaivicResourceBundle() {
		String impl="net.maivic.comm.impl.";
		defaultConstructibleImplementations.put("TransportManager", impl+"TransportManagerImpl");
		defaultConstructibleImplementations.put("NameService", "net.maivic.comm.NameService.NameServiceImpl");
		addDefaultConstrucibleClassByName(impl+"BaseTypeMapper");
		addDefaultConstrucibleClassByName("net.maivic.comm.ThreadManager");
		addComplexConstructibleClass("net.maivic.netty.SocketClient");
		classRefernces.put("Transport(nettytcp)", "net.maivic.netty.NettyTransportImpl");
		defaultConstructibleImplementations.put("net.maivic.comm.SubscriptionCenter",
				"net.maivic.comm.impl.SubscriptionCenterImpl");
		addConstant("default.nettytcp.port", 12345);
		Class<?> clz =null;
		try {
			clz= Class.forName("io.netty.channel.nio.NioEventLoopGroup");
			this.defaultConstructibleImplementations.put(ScheduledExecutorService.class.getName(), "io.netty.channel.nio.NioEventLoopGroup"  );
		}catch (ClassNotFoundException e) {	
			this.constantValues.put(ScheduledExecutorService.class.getName(), Executors.newSingleThreadExecutor());
		}
		/**
		 * see also maivic-bridge/src/python/tornado_server/pb/services.py, _services definition
		 * The values of ServiceID must correspond!
		 */
		this.constantValues.put("ServiceID.List", 1);
		this.constantValues.put("ServiceID.Echo", 2);
		this.constantValues.put("ServiceID.NameService", 3);
		this.constantValues.put("ServiceID.RPC",  4);	
		this.constantValues.put("RelationsRPC.prefix", "DBRelation");
		
	}
	private <T> void addConstant(String name, T constant) {
		this.constantValues.put(name, constant);
	}
	private void addComplexConstructibleClass(String string) {
		classRefernces.put(string, string);
		
	}
	private  void addDefaultConstrucibleClassByName(String classname){
		defaultConstructibleImplementations.put(classname, classname);

		
	}
	@Override
	protected Object handleGetObject(String key) {
		if(key == "Config") return this;
		if (defaultConstructibleImplementations.containsKey(key)) {
			String classname = defaultConstructibleImplementations.get(key);
			try {
				return Class.forName(classname).getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}else if(classRefernces.containsKey(key)){
			try {
				return Class.forName(classRefernces.get(key));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				
				return null;
			}
			
		} else if(constantValues.containsKey(key)){
			return constantValues.get(key);
		}
		return null;
	}

	@Override
	
	public Enumeration<String> getKeys() {
		return new Enumeration<String>(){
			Enumeration<String> e1 = defaultConstructibleImplementations.keys();
			Enumeration<String> e2 = classRefernces.keys();
			Enumeration<String> e3 = constantValues.keys();
 			Enumeration<String> current=e1;
			public boolean hasMoreElements() {
				if(current==e1 && !current.hasMoreElements()){
					current=e2;
				}
				if(current ==e2 && !current.hasMoreElements()){
					current=e3;
				}
				return current.hasMoreElements();
			}

			public String nextElement() {
				// hasmoreelements assures transition between e1 and e2;
				hasMoreElements();
				
				return current.nextElement();
				
			}
			
		};
	}

}
