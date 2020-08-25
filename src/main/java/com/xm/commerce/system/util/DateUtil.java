package com.xm.commerce.system.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final ThreadLocal<SimpleDateFormat> NORMAL_DATE_TIME_FORMAT =
			ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	public static String dateToStrLong(Date dateDate) {
		return NORMAL_DATE_TIME_FORMAT.get().format(dateDate);
	}

	public static Date strToDate(String strDate) {
		return NORMAL_DATE_TIME_FORMAT.get().parse(strDate, new ParsePosition(0));
	}
}
