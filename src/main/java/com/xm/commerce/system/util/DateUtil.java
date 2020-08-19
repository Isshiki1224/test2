package com.xm.commerce.system.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String dateToStrLong(Date dateDate) {
		return DATE_FORMAT.format(dateDate);
	}

	public static Date strToDate(String strDate) {
		return DATE_FORMAT.parse(strDate, new ParsePosition(0));
	}

	public static Date dateNow() {
		Date currentTime = new Date();
		DATE_FORMAT.format(currentTime);
		return currentTime;
	}

	public static Date localDate2Date(LocalDate now) {
		if (null == now) {
			return null;
		}
		ZonedDateTime startOfDay = now.atStartOfDay(ZoneId.systemDefault());
		return Date.from(startOfDay.toInstant());
	}

	public static Date previousDateMinute(int beforeMinute) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateNow());
		c.add(Calendar.MINUTE, -beforeMinute);

		return c.getTime();
	}
}
