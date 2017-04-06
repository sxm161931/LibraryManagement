package com.test.utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	public static String getCurrentDate()
	{
		Date date = new Date();

		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(date);
		return currentTime;
	}
	
	public static String getDueDate()
	{
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.DATE, 14); //2 weeks
	    java.text.SimpleDateFormat sdf = 
			     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dueDate =sdf.format(calendar.getTime());
	   return dueDate;
	}
	
	public static long getDateDiff(String date1,String date2)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date firstParsedDate =null, secondParsedDate = null;
		long diff = 0;
		try {
			firstParsedDate = dateFormat.parse(date1);
			System.out.println(firstParsedDate);
			secondParsedDate = dateFormat.parse(date2);
			System.out.println(secondParsedDate);
			
			// = dateFormat.parse(getCurrentDate());
			
			long diffDays1 = (long) Math.ceil(firstParsedDate.getTime() / (24 * 60 * 60 * 1000));
			long diffDays2 = (long) Math.ceil(secondParsedDate.getTime() / (24 * 60 * 60 * 1000));
			System.out.println(diffDays1);
			System.out.println(diffDays2);
			System.out.println(diffDays1 - diffDays2);
			diff = Math.abs(diffDays1 - diffDays2);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		//System.out.println(((firstParsedDate.getTime()-currentTime.getTime())/(1000 * 60 * 60 * 24)) - ((secondParsedDate.getTime()-currentTime.getTime())/(1000 * 60 * 60 * 24)));
		//long diff = (long) Math.ceil(Math.abs(firstParsedDate.getTime() - secondParsedDate.getTime())/(1000 * 60 * 60 * 24));
		return diff;
	}
	
	
	public static String getStringDate(Timestamp date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String string  = dateFormat.format(date);
		return string;
	}
}
