package net.maivic.context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import net.maivic.comm.SubscriptionCenter;
import net.maivic.comm.ThreadManager;
import net.maivic.comm.TransportManager;
import net.maivic.comm.service.NameService;
/**
 * Context Singleton managing the factories of maivic comm 
 * @author paul@maivic.com
 * 
 */
public final class Context {
	private static Context instance;
	private Set<String> nativeSupporteedEntityTypes = new HashSet<String>(Arrays.asList(new String[]{
			"TransportManager",
			"Log"
	}));
	private Map<String,Object> supportedEntityTypes = new HashMap<String, Object>();
	private ResourceBundle resources = new MaivicResourceBundle(); 
	private Map<String,Class<?>> enforceType = new HashMap<String, Class<?>>(); 
	private Context(){
		this.supportedEntityTypes.put("TransportManager", null);		
		this.enforceType.put("net.maivic.comm.TransportManager", TransportManager.class);
		this.supportedEntityTypes.put("Logger", null);		
		this.enforceType.put("net.maivic.context.Log", Log.class);
	}
	
	
	public static Context get(){
		if (Context.instance!=null){
			return Context.instance;
		}else{
			Context.instance=new Context();
			return Context.instance;
		}
	}
	public Set<String>supportedEntityTypes(){
		return this. supportedEntityTypes.keySet();
	}
	
	/**
	 *Sets a supported entity which can be registerd via the {@link Context.register()} method
	 * @param entity a string descriptor of entity
	 * @param type The type of the value for the entity string. Set to null for avoing type checking
	 * when register is used.
	 */
	public void setSupportedEntity(String entity, Class<?> type){
		if (this.nativeSupporteedEntityTypes.contains(entity)){
			throw new IllegalArgumentException("Cannot reset the supportedentity for a native type");
		}
		this.supportedEntityTypes.put(entity, null);
		if (type != null){
			this.enforceType.put(entity, type);
		}
	}
	/**
	 * Same as {@link Context.setSupportedEntity(String entity, Class<?> type)} but
	 * with type argument set to null (no type checking)
	 */
	public void setSupportedEntity(String entity){
		this.setSupportedEntity(entity, null);
	}
	
	public void removeSupportedEntity(String e) throws EntityException{
		if (supportedEntityTypes.containsKey(e)){
			if(nativeSupporteedEntityTypes.contains(e)){
				throw new  EntityException( "Cannot remove natively supported entity type");
			} else{
				if (enforceType.containsKey(e)){
					enforceType.remove(e);
				}
				supportedEntityTypes.remove(e);
			}
		}
	}
/**
 * register an entity to be returned on subsequent calls to get(entity)
 * @param entity the entity desctiptor
 * @param o the entity instance
 * @throws WrongType if trying to register an entity for which type checking is enabled and the type is wrong 
 * @throws UnsupportedType 
 */
	public void register (String entity, Object o) throws WrongType, UnsupportedType{
		try {
			register(entity, o, false);
		} catch (EntityAlreadyRegistered e) {
			throw new IllegalStateException("register may not throw with force - false!");
		}
	}
	/**
	 * This function registers an entity. if an entity type has been supplied with {@link Context.setSupportedEntity(String entity, Class<?> type)}
	 * then it is checked that the object parameter is of that type.
	 * Also only overrides an existing value if force is set not true.
	 * @param entity the entity descriptor
	 * @param o the entity instance
	 * @param force if true regstration goes ahead even if an entity is already registered.
	 * @throws EntityAlreadyRegistered when the entity has already been registered and force == false
	 * @throws WrongType if the typechecking is on (registered with {@link Context.setSupportedEntity(String entity, Class<?> type)} with type argument set tot true
	 * @throws UnsupportedType if the entity argument is not supported (you can add it via the setSupportedEntity method
	 */
	
	public void register(String entity, Object o, boolean force) throws EntityAlreadyRegistered, WrongType, UnsupportedType {
		if(force == true || supportedEntityTypes.get(entity) == null){
			if (enforceType.containsKey(entity)){
				if ( !  o.getClass().isAssignableFrom(enforceType.get(entity))) {
					throw new WrongType(o, enforceType.get(entity)); 
				}
				
			}
			supportedEntityTypes.put(entity, o);
			
		}
		else {
			throw new EntityAlreadyRegistered(entity);
		}
	
	}
	/**
	 *  Returns a registered entity, or null if the entity is not
	 * set or not supported
	 *  @param entity the entity descriptor
	 *  @return the stored entity or null if none was set or entity is not supported
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String entity){
		if ( supportedEntityTypes.containsKey(entity)) {
			T ret = (T)supportedEntityTypes.get(entity);
			if(ret==null) {
				ret = (T)this.resources.getObject(entity);
				if (ret != null) {
					this.supportedEntityTypes.put(entity, ret);
				}
			}
			return ret;
		} else {
			T ret = (T)resources.getObject(entity);
			if(ret!=null) {
				try {
					register(entity, ret);
				} catch (Exception e) {
					e.printStackTrace();
					
					return null;
				}
			}
			return ret;
		}
	}
	/**
	 * Same as {@link Context.get(String entity)} but throws an exception
	 * if the entity is not supported  
	 * @param entity
	 * @return the stored entity or none if no entity was set
	 * @throws InvalidEntity if entity is not supported
	 */
	public Object getOrThrow(String entity) throws InvalidEntity {
		if ( supportedEntityTypes.containsKey(entity)) {
			return supportedEntityTypes.get(entity);
		} else {
			throw new InvalidEntity(entity);
		}
	}
	
	public TransportManager getTransportManager(){
		return (TransportManager) get("TransportManager");
	
	}
	public Log log(){
		Log ret= (Log)this.supportedEntityTypes.get("Log");
		if(ret==null){
			ret=new Log();
			try {
				this.register("Log", ret);
			} catch (WrongType e) {
				
			} catch (UnsupportedType e) {
				
			}
		}
		return ret;
	}
	

	public SubscriptionCenter getSubscriptionCenter() {
		return get("net.maivic.comm.SubscriptionCenter");
	}


	public ThreadManager getThreadManager() {
		return (ThreadManager) get("net.maivic.comm.ThreadManager");
	}


	public NameService getNameService() {
		return (NameService) get("NameService");
	}
}