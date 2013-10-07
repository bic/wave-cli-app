package com.maivic.android;

import java.math.BigDecimal;

public class Utils {
	private static BigDecimal zero = new BigDecimal(0); 
	
	
	public static BigDecimal nextWithoutReference(BigDecimal value, BigDecimal step, BigDecimal min, BigDecimal max) {
		return next(value, step, value, min, max);
	}
	
	public static BigDecimal next(BigDecimal value, BigDecimal step, BigDecimal reference, BigDecimal min, BigDecimal max) {
	
		BigDecimal limit = null;	
		int cmp=step.compareTo(zero);
		if(cmp <0) {
			limit=min;
		} else if (cmp > 0) {
			limit=max;
		} else {
			throw new IllegalArgumentException("step provided to next must not be 0!");
		}
		BigDecimal range =reference.subtract(value);
		// Look at the link below for an explanation for the next line 
		// http://stackoverflow.com/questions/4412179/best-way-to-make-javas-modulus-behave-like-it-should-with-negative-numbers/4412200#4412200
		
		BigDecimal next_step= range.remainder(step).add(step).remainder(step);
		if (next_step.equals(zero)) {
			next_step = step;
		}
		next_step=value.add(next_step);
		if(cmp ==  next_step.compareTo(limit) ){
			return limit;
		} else {
			return next_step;
		}
	}
}
