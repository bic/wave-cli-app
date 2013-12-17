package net.maivic.comm.utils;

public class Math {
	public static int floorDiv(int a, int b) { 
        return (a >= 0 ? a / b : ((a + 1) / b) - 1); 
    } 
}
