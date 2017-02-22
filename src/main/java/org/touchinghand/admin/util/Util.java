package org.touchinghand.admin.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Util {
	
	
	public static LocalDate localDateNow(){
		return LocalDate.now();
	}
	
	public static LocalDateTime localDateTimeNow(){
		return LocalDateTime.now();
	}
	
	public static java.sql.Date getCurrentSqlDate(){
		
		return java.sql.Date.valueOf(localDateNow());
	}
	
	public static Timestamp toTimeStamp(LocalDateTime ldt){
		
		return ldt == null ? null : Timestamp.valueOf(ldt);
	}
	
	public static LocalDateTime toLocalDateTime(Timestamp ts){
		
		return ts == null ? null : ts.toLocalDateTime();
	}
}
