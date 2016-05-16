package utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Formatter {
	
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
	public static String formatDouble(Double value) {
		return getDoubleFormat().format(value);
	}

	private static DecimalFormat getDoubleFormat() {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("#0.00", otherSymbols);
		return df;
	}
}
