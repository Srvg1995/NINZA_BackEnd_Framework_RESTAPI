package com.ninza.hrm.api.genericutility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
/**
 * @author gagan
 */
public class JavaUtility {
	/**
	 * get the random number in the range of 0-5000
	 * @return
	 */
	public int getRandomNumber() {
		Random random=new Random();
		int ranDomNumber = random.nextInt(5000);
		return ranDomNumber;
	}
	
	
	/**
	 * get the system date based on YYYY-MM-DD
	 * @return
	 */
	public String getSystemDateYYYYMMDD() {
		Date dateObj=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(dateObj);
		return date;
	}

	public String getRequiredDateYYYYMMDD(int days) {
		Date dateObj=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		sim.format(dateObj);
		Calendar cal = sim.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH,days);
		String reqDate = sim.format(cal.getTime());
		return reqDate;
	}
	
	
	
}
