package com.maivic.android.utils;

import java.math.BigDecimal;
//import net.maivic.protocol.Model.Decimal.Builder;
/**
 * Class aggregating helper functions for the numberpicker
 * @author paul
 *
 */
public class Utils {
	private static BigDecimal zero = new BigDecimal(0); 
	/**
	 * calculates the next value using the current value as reference
	 * @See {@link Utils.next(BigDecimal value, BigDecimal step, BigDecimal reference, BigDecimal min, BigDecimal max)} 
	 */
	
	public static BigDecimal nextWithoutReference(BigDecimal value, BigDecimal step, BigDecimal min, BigDecimal max) {
		return next(value, step, value, min, max);
	}
	
	/**
	 * Function calculating the next numberpicker value given the current value as in the {@code value} parameter.
	 * The function has special behaivors around limits:
	 * if value = min or max then:
	 *  next value is rounded to the next value where (reference -newvalue) % step == 0
	 * if value+step is >max or < min the next step is max or min respectively 
	 * 
	 * @param value the current value
	 * @param step the step for calculating the next value
	 * @param reference the reference value for stepping
	 * @param min minimum value
	 * @param max maximum value
	 * @return the next value
	 * @throws IllegalArgumentException for step==0
	 */
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
		if (next_step.compareTo(zero)==0) {
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
