package net.maivic.comm;

import java.util.Iterator;

import org.w3c.dom.ranges.RangeException;

import net.maivic.protobuf.Message.MaivicMessage;
import net.maivic.protobuf.Message.SequenceItem;
/**
 * Abstract {@link MaivicMessage} Iterator for easy implementation of the
 * streaming Interface;
 * @author paul
 *
 */
public abstract class AbstractMessageIterator implements Iterator<MaivicMessage> {
	
	long index=0;
	long size=0;
	long last_estimated_size=0;
	long next_estimation_at_idx;
	public abstract boolean hasNext();

	public MaivicMessage next() {
		MaivicMessage.Builder mess = next_without_sequence();
		SequenceItem.Builder seq = SequenceItem.newBuilder()
				.setIndex(index);
		index++;
		long est= this.estimate_size();
		if (index == next_estimation_at_idx) {
			next_estimation_at_idx = next_estimation_step();
			if ( est > 0){
				if (index< est) {
					seq.setEstimatedLength(est);
				}else{
					throw new  RangeException( RangeException.BAD_BOUNDARYPOINTS_ERR ,"Index " +est+" is already passed the estimated size of: " + est);
				}
		
			}
		}
		
		//only set size if it has not already been set
		if (size ==0 ){
			long size= this.size();
			if ( size > 0){
				if (index< size) {
					seq.setLength(size);
				}else{
					throw new  RangeException( RangeException.BAD_BOUNDARYPOINTS_ERR ,"Index " +est+" is already passed the size of: " + est);
				}
			}		
		}
		mess.setStreamSequenceItem(seq);
		
		return mess.build();
	}
	private long next_estimation_step() {
		
		return next_estimation_at_idx +1 ;
	}

	public abstract MaivicMessage.Builder next_without_sequence();

	public int estimate_size(){
		return 0;
	}
	public int size() {
		return 0;
	}
	public final void remove() {
		throw new UnsupportedOperationException( "Cannot remove a Message from a message Sequence");

	}

}