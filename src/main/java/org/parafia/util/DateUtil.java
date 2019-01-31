package org.parafia.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parafia.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 *  to correct time pattern. Minutes should be mm not MM (MM is month). 
 */
public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static final String TIME_PATTERN = "HH:mm";
    
	private static final String[] months = {"stycznia", "lutego", "marca", "kwietnia", "maja", "czerwca", "lipca", "sierpnia", "września", "października", "listopada", "grudnia"};

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private DateUtil() {
    }

    /**
     * Return default datePattern (MM/dd/yyyy)
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        /*Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale)
                .getString("date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "dd-MM-yyyy";
        }

        return defaultDatePattern;*/
        
        String datetimeFormat = MessageResources.getMessage("date.format");
    	if (datetimeFormat != null)
    		return datetimeFormat;
    	else
    		return "dd-MM-yyyy";
    }

    public static String getDateTimePattern() {
        /*Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale)
                .getString("datetime.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "dd-MM-yyyy HH:mm:ss";
        }

        return defaultDatePattern;*/
    	
    	String datetimeFormat = MessageResources.getMessage("datetime.format");
    	if (datetimeFormat != null)
    		return datetimeFormat;
    	else
    		return "dd-MM-yyyy HH:mm:ss";
    }
    
    public static boolean checkDateFormat(String date, String format) {
    	try {
    	    DateFormat df = new SimpleDateFormat(format);
            df.parse(date);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * This method attempts to convert an Oracle-formatted date
     * in the form dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @see java.text.SimpleDateFormat
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String aMask, String strDate)
      throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * MM/dd/yyyy HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     * 
     * @return the current date
     */
    public static Calendar getToday() {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        try {
        	cal.setTime(convertStringToDate(todayAsString));
        } catch (ParseException ex) {
        	//should not ever happen
        	log.error("Bug in the code!!!", ex);
        }

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * 
     * @see java.text.SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     * 
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     * 
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String strDate)
      throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }

            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(),
                                     pe.getErrorOffset());
        }

        return aDate;
    }
    
    public static Date getFirstDayOfTheYear(int year) {
    	Calendar gc = new GregorianCalendar();
    	gc.set(Calendar.YEAR, year);
    	gc.set(Calendar.MONTH, 0);
    	gc.set(Calendar.DAY_OF_MONTH, 1);
    	gc.set(Calendar.HOUR_OF_DAY, 0);
    	gc.set(Calendar.MINUTE, 0);
    	gc.set(Calendar.SECOND, 0);
    	gc.set(Calendar.MILLISECOND, 0);
    	return gc.getTime();
    }
    
    public static Date getLastDayOfTheYear(int year) {
    	Calendar gc = new GregorianCalendar();
    	gc.set(Calendar.YEAR, year + 1);
    	gc.set(Calendar.MONTH, 0);
    	gc.set(Calendar.DAY_OF_MONTH, 1);
    	gc.set(Calendar.HOUR_OF_DAY, 0);
    	gc.set(Calendar.MINUTE, 0);
    	gc.set(Calendar.SECOND, 0);
    	gc.set(Calendar.MILLISECOND, 0);
    	gc.add(Calendar.MILLISECOND, -1);
    	return gc.getTime();
    }
    
    /**
     * Calculates year difference between two dates
     * @param a
     * @param b
     * @return years of the difference
     */
    public static int calculateDifference(Date a, Date b)
    {
        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();
     
        if (a.compareTo(b) < 0) {
            earlier.setTime(a);
            later.setTime(b);
        } else {
            earlier.setTime(b);
            later.setTime(a);
        }
        
        return later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR);
    }
    
    /**
     * Calculates year difference between two dates
     * @param a - string, YYYY or DD-MM-YYYY
     * @param b
     * @return years of the difference
     */
    public static int calculateDifference(String a, Date b) {
        try {
	        if (a.length() == 4) {				//podany sam rok
	        	Calendar cal = Calendar.getInstance();
	        	cal.setTime(b);
	        	
	        	SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
	        	return Math.abs(cal.get(Calendar.YEAR) - 1900 - Integer.parseInt(df.format(a)));
	        } else {
	        	Calendar earlier = Calendar.getInstance();
	            Calendar later = Calendar.getInstance();
	            SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	            Date a1 = df.parse(a);
	            
		        if (a1.compareTo(b) < 0) {
		            earlier.setTime(a1);
		            later.setTime(b);
		        } else {
		            earlier.setTime(b);
		            later.setTime(a1);
		        }
		        
		        return later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR);
	        }
        } catch (Exception ex) {
        	System.err.println(ex);
        	return 0;
        }
    }
    
    
    public static String getPolishFormatDate(Date date) {
    	DateFormat df = new SimpleDateFormat("d M yyyy");
    	String res = df.format(date);
    	String[] res2 = res.split(" ");
    	res2[1] = months[Integer.parseInt(res2[1]) - 1];
    	res = "";
    	for (String s : res2) {
    		res += s + " ";
    	}
    	return res.substring(0, res.length() - 1);
    }
    
    
    public static Date mergeDateAndTime(Date date, String time) {
    	if (StringUtils.isNotBlank(time)) {
    		int hours;
    		int minutes;
    		
    		if (time.contains(":")) {
    			hours = Integer.valueOf(time.split(":")[0]);
    			minutes = Integer.valueOf(time.split(":")[1]);
    		} else {
    			hours = Integer.valueOf(time.trim());
    			minutes = 0;
    		}
    		
    		long milisecs = date.getTime();
    		
    		milisecs += 3600000 * hours;
    		milisecs += 60000 * minutes;
    		
    		date = new Date(milisecs);
    	}
    	
    	return date;
    }
}
