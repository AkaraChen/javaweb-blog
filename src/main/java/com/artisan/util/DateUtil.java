package com.artisan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// Date 转 String
	public static String encode(Date date) {
		return simpleDateFormat.format(date);
	}

	// String 转 Date
	public static Date decode(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
}
