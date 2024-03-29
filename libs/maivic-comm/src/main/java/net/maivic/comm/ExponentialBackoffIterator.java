package net.maivic.comm;

import java.util.Iterator;

public class ExponentialBackoffIterator implements Iterator<Long> {
	private int i =0;
	private int maxretry;
	public ExponentialBackoffIterator(int maxretry) {
		this.maxretry=maxretry;
	}
	public boolean hasNext() {
		return i < maxretry;
	}

	public Long next() {
		i++;
		return  Math.round((1.0/2.0)*(Math.pow(2, i-1)-1));
	}
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove makes no sense here!!");
	}
}
