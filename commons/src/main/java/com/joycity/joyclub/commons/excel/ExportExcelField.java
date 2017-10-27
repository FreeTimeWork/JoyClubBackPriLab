package com.joycity.joyclub.commons.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportExcelField {
	
	public String columnName();

	public int sequence() default Integer.MAX_VALUE;
	
}
