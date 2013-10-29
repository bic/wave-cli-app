package com.maivic.android;

import static org.junit.Assert.assertArrayEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.maivic.android.utils.Utils;

public class UtilsTest {

	@Test
	public void test_min_wo_reference() {
		int values[]  = new int[]{ 0, -1,-2,-1, 1, 1, 1};
		int steps[]   = new int[]{-1, -1,-1,-2, 2, 2, 2}; 
		int mins []   = new int[]{-2, -2,-2,-2, 0, 0, 0};
		int maxs []   = new int[]{ 2,  2, 2, 2, 2, 6, 2};
		int exp_res[] = new int[]{-1, -2,-2,-2, 2, 3, 2};
		int real_res[]= new int[exp_res.length];
		for (int i=0; i<values.length; i++){
			real_res[i]=Utils.nextWithoutReference(new BigDecimal(values[i]), 
									new BigDecimal(steps[i]), 
									new BigDecimal(mins[i]), 
									new BigDecimal(maxs[i])).intValue();
		}
		assertArrayEquals(exp_res,real_res);
	}
	@Test
	public void test_min_with_reference() {
		int values[]  = new int[]{ 0,-1,-3,-4, 2, 2, 2, 2, 1,-1};
		int steps[]   = new int[]{-1,-2, 2, 2, 2, 2, 2, 2, 5,-5}; 
		int refs[]    = new int[]{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
		int mins []   = new int[]{-2,-2,-3,-4, 0, 0, 0, 0, 0,-3};
		int maxs []   = new int[]{ 2, 2, 2, 2, 2, 6, 4, 3, 3, 3};
		int exp_res[] = new int[]{-1,-2,-2,-2, 2, 3, 4, 3, 3,-3};
		int real_res[]= new int[exp_res.length];
		for (int i=0; i<values.length; i++){
			real_res[i]=Utils.next(new BigDecimal(values[i]), 
									new BigDecimal(steps[i]),
									new BigDecimal(refs[i]),
									new BigDecimal(mins[i]), 
									new BigDecimal(maxs[i])).intValue();
		}
		assertArrayEquals(exp_res,real_res);
	}

	
	
}
