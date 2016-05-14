package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {
	
	public static String formatDate(Date date) {
		return getDateFormat().format(date);
	}

	public static Date getDate(String source) throws ParseException {
		return getDateFormat().parse(source);
	}
	
	private static DateFormat getDateFormat() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		return dateFormat;
	}
}
