package ar.com.syswarp.utils;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class DateAndTimeUtils {

	public static Timestamp getCurrentDateAsTimestamp() {
		return new Timestamp(new GregorianCalendar().getTime().getTime());
	}
	
}
