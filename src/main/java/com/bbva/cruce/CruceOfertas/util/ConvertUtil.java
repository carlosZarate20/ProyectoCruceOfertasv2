package com.bbva.cruce.CruceOfertas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConvertUtil {

	private static final Logger LOGGER = LogManager.getLogger(ConvertUtil.class);

	private ConvertUtil() {
	}

	public static XMLGregorianCalendar convertDateToXmlGregorianCalendar(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	public static String convertDateToString(Date date, String format) {
		if (date == null || format == null) {
			return null;
		}
		try {
			DateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		} catch (Exception e) {
			LOGGER.warn("Error al convertir a fecha: " + e.getMessage());
			return null;
		}
	}

	public static Long convertToLong(Object source) {
		if (source == null) {
			return null;
		}
		if (source instanceof Long) {
			return (Long) source;
		}
		try {
			return Long.valueOf(source.toString());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Integer convertToInteger(Object source) {
		if (source == null) {
			return null;
		}
		if (source instanceof Integer) {
			return (Integer) source;
		}
		try {
			return Integer.valueOf(source.toString());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Date convertToDate(String dateStr, String format) {
		if (dateStr == null || format == null) {
			return null;
		}
		try {
			DateFormat df = new SimpleDateFormat(format);
			return df.parse(dateStr);
		} catch (ParseException e) {
			LOGGER.warn("Error al convertir a fecha: " + e.getMessage());
			return null;
		}
	}

	public static Double convertToDouble(Object source) {
		if (source == null) {
			return null;
		}
		if (source instanceof Double) {
			return (Double) source;
		}
		try {
			return Double.valueOf(source.toString());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Character convertToCharacter(Object source) {
		if (source == null) {
			return null;
		}
		String str = source.toString();
		if (str.isEmpty()) {
			return null;
		}
		return str.charAt(0);
	}
}
