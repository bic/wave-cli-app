package net.maivic.comm.NameService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import net.maivic.comm.Callback;
import net.maivic.comm.Maivic.NameSpaceUpdate;
import net.maivic.comm.RoutingFilter;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.comm.service.AbstractRPCServiceClient;
import net.maivic.comm.service.NameService;
import net.maivic.comm.utils.Pair;
import net.maivic.comm.utils.Utils;
import net.maivic.context.Context;
import net.maivic.context.Log;

public class NameServiceImpl implements NameService {
	private Log log= Context.get().log();
	private String TAG = NameServiceImpl.class.getName();
	private Map<Integer,NameSpaceUpdate> keyRequests = new HashMap<Integer, NameSpaceUpdate>();
	private int serviceId;
	ReadWriteLock datasourcelock= new ReentrantReadWriteLock();
	Map<Long,Map<Long,String>> datasource = new HashMap<Long,Map<Long,String>>();
	static class StringLookup {
		Map<String,Long> fw = new HashMap<String, Long>();
		Map<Long,String> rev = new HashMap<Long, String>();
		ReadWriteLock lock=new ReentrantReadWriteLock();
		
		public void insert(long key, String value ){
			lock.writeLock().lock();
			try{
				fw.put( value, key);
				rev.put(key, value);
			} finally{
				lock.writeLock().unlock();
			}
		}
		public void remove(long key) {
			lock.writeLock().lock();
			try{
				String value = rev.remove(key);
				if (value != null) {
					fw.remove( value);
				}
			} finally{
				lock.writeLock().unlock();
			}	
		}
		public long forward( String name){
			lock.readLock().lock();
			try{
				return fw.get(name);
			}finally{
				lock.readLock().unlock();
			}
			
		}
		public String reverse( long id){
			lock.readLock().lock();
			try{
				return rev.get(id);
			}finally{
				lock.readLock().unlock();
			}
		}
		public void reset() {
			lock.writeLock().lock();
			try{
				this.fw.clear();
				this.rev.clear();
			} finally{
				lock.writeLock().lock();
			}
			
		}
	}
	static StringLookup stringLookup=new NameServiceImpl.StringLookup();
	protected NameServiceImpl(int serviceId) {
		this.init(serviceId);
	}
	private void init(int serviceId) {
		this.serviceId = (Integer)Context.get().get("ServiceID.NameService");
		Context.get().getSubscriptionCenter().addSubscription(new RoutingFilter<MessageContainer>()
		{

			public boolean route(MessageContainer mess) {
				return mess.getServiceId() == NameServiceImpl.this.serviceId;
			}
		}, new Callback<MessageContainer>()  {

			public void call(MessageContainer result) {
				NameServiceImpl.this.handleIncoming(result);
			}
		});
	}
	
	public NameServiceImpl() {
		this.init((Integer)Context.get().get("ServiceID.NameService"));
	}

	protected void handleIncoming(MessageContainer result) {
		NameSpaceUpdate update = null;
		try {
			 update=NameSpaceUpdate.parseFrom(result.getContent());
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.e(TAG, "desreialization Error on incoming message!! result.content=" + Utils.toHexString(result.getContent().toByteArray()));
			//TODO: Send Error Message to server
		}
		if (result.getThreadControl().getInThreadSeq() !=1){
			throw new UnsupportedOperationException("Can only be client in NameSpaceUpdate protocol");
		} 
		
		this.datasourcelock.writeLock().lock();
		try{
			NameSpaceUpdate request = this.keyRequests.get(result.getThreadControl().getThreadId());
			Iterator<ByteString> val_iter= update.getValuesList().iterator();
			Iterator<Long> skip_iter=update.getSkippedIdsList().iterator();
			//String domain = stringLookup.reverse(request.getDomain());
			switch( update.getOp()){
			case READ:
				
 				long skip_next=-1;
 				if (skip_iter.hasNext()){
 					skip_next=skip_iter.next();
 				}
 				Map<Long, String> domain_o = get_or_put_empty(request);
				for ( long r : request.getIdsList() ){
					
					if ((skip_next > 0) && (r == skip_next)){
						domain_o.remove(skip_next);
						if (skip_iter.hasNext()){
		 					skip_next=skip_iter.next();
		 				}else{
		 					skip_next=-1;
		 				}
					}else if (val_iter.hasNext() ){
						domain_o.put(r, val_iter.next().toString());
					}
				}
				break;
			case READ_ALL_EXCEPT:
				domain_o = get_or_put_empty(request);
				for ( long r : request.getIdsList() ){
					domain_o.put(r, val_iter.next().toString());
				}
				break;
			case RESET_DOMAIN:
				long domain = update.getDomain();
				datasource.remove(domain);	
				
				
				break;
			case WRITE:
				throw new UnsupportedOperationException("Write Operation not supported from client");
				//break;
			default:
				break;
			
			}
			
		} finally{
			this.datasourcelock.writeLock().unlock();
		}
	}

	private Map<Long, String> get_or_put_empty(NameSpaceUpdate request) {
		Map<Long, String> domain_o = datasource.get(request.getDomain());
		if (domain_o==null) {
			domain_o= new HashMap<Long, String>();
			datasource.put(request.getDomain(), domain_o);
		}
		return domain_o;
	}

	
	public List<String> getValues(List<String> keys) {
		ArrayList<String> ret = new ArrayList<String>(keys.size());
		datasourcelock.readLock().lock();
		try{
			for (int i = 0; i < keys.size(); i++){
				ret.set(i, this.getValue(keys.get(i)));
			}
		}finally{
			datasourcelock.readLock().unlock();
		}
		return ret;
	}

	public String getValue(String key) {
		int split = key.lastIndexOf('.');
		String domain="";
		if(split ==-1){
			domain=key.substring(0, split);
			key=key.substring(split+1);
		}
		long key_l =stringLookup.forward(key);
		long domain_l=stringLookup.forward(domain);
		datasourcelock.readLock().lock();
		try{
			Map<Long, String> domain_o = this.datasource.get(domain_l);
			if(domain_o != null) return domain_o.get(key_l);
			return null;
		} finally{
			datasourcelock.readLock().unlock();
		}
	}
	public void setValue(String key, String value) {
		int split = key.lastIndexOf('.');
		String domain="";
		if(split ==-1){
			domain=key.substring(0, split);
			key=key.substring(split+1);
		}
		long key_l =stringLookup.forward(key);
		long domain_l=stringLookup.forward(domain);
		datasourcelock.readLock().lock();
		try{
			Map<Long, String> domain_o = this.datasource.get(domain_l);
			if (domain_o == null){
				domain_o=this.create_domain();
				this.datasource.put(domain_l, domain_o);
			}
			domain_o.put(key_l, value);
		} finally{
			datasourcelock.readLock().unlock();
		}
	}

	private Map<Long, String> create_domain() {
		return new HashMap<Long, String>();	
	}

	public void setValues(List<String> keys, List<String> values) {
		// TODO Auto-generated method stub
		
	}
	

}
