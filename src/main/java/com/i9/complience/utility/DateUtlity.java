package com.i9.complience.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtlity {

	public static Date convertStringToDate(String val) {
		Date convertedDate = null;
		if (val != null && !val.isEmpty()) {
			if (val.contains("/")) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
				try {
					convertedDate = simpleDateFormat.parse(val);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd yyyy");
				try {
					convertedDate = simpleDateFormat.parse(val);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return convertedDate;
	}

	public static String convertDateToString(Date dateCellValue) {
		if (dateCellValue != null) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String strDate = dateFormat.format(dateCellValue);
			return strDate;
		} else {
			return null;
		}
	}

	public static String convertDateToMMMMddyyyy(Date dateCellValue) {
		if (dateCellValue != null) {
			DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
			String strDate = dateFormat.format(dateCellValue);
			return strDate;
		} else {
			return null;
		}
	}

	public static String currentDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String str = formatter.format(date);
		return str;
	}

	public static boolean compareDates(String i9Date, String expiryDate) {
		Date i9date = convertStringToDate(i9Date);
		Date exDate = convertStringToDate(expiryDate);
		if (i9date.compareTo(exDate) > 0) {
			return true;
		} else if (i9date.compareTo(exDate) == 0) {
			return true;
		} else {
			return false;
		}
	}
}
