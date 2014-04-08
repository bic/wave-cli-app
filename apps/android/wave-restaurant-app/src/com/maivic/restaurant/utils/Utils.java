package com.maivic.restaurant.utils;

import java.math.BigDecimal;

import net.maivic.protocol.Model.Decimal;
import net.maivic.protocol.Model.DecimalOrBuilder;
import net.maivic.protocol.Model.Decimal.Builder;;

public class Utils {
	
	public static BigDecimal bigDecimalFromDecimal(Decimal decValue){
		return BigDecimal.valueOf(decValue.getValue(), decValue.getScale());
		
	}
	
	public static DecimalOrBuilder decimalFromBigDecimal(BigDecimal bDecValue, Builder decimalValue){
		if (decimalValue==null) {
			decimalValue = Decimal.newBuilder();
		}
		
		decimalValue.setScale(bDecValue.scale());
		decimalValue.setValue(bDecValue.unscaledValue().longValue());
		return decimalValue;
	}

}
