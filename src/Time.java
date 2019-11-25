import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 */
public class Time {
	private final String[] MONTHS = {"January","February","March","April","May","June","July","August","September","October","November","December"};


	//Time Storage Calendar Object
	private GregorianCalendar calendar;


	//GST Time
	private int day; //1-monthlength, if day exceeds monthlength, goes into the next month
	private int month; //0-11
	private int year; //0-...
	private int hour; //0-24
	private int minute; //0-60
	private int second; //0-60
	
	//Sidereal Time
	private int Shour; //0-24
	private int Sminute; //0-60
	private int Ssecond; //0-60

	/**
	 *
	 */
	public Time(){
		now();

	}

	/**
	 *
	 */
	public void print(){
		System.out.println(calendar.getTime());
		
		System.out.print(day);
		System.out.print(' ');
		System.out.print(MONTHS[month-1]);
		System.out.print(' ');
		System.out.print(year);
		System.out.print(' ');
		System.out.print(hour);
		System.out.print(':');
		System.out.print(minute);
		System.out.print(':');
		System.out.println(second);

		System.out.print(Shour);
		System.out.print(':');
		System.out.print(Sminute);
		System.out.print(':');
		System.out.println(Ssecond);
	}

	/**
	 * Greenwich Mean Siderial Time
	 * @return
	 */
	private double GST(){

		double d = (jd() -2451545.0);
		double T = d / 36525.0;
		double temp = 6.697374558+ (.06570982441908 * d) + 1.00273790935 * (hour + (minute / 60.0) + (second/3600.0)) + .000026 * Math.pow(T,2);
		//temp = temp %24;
		/*
    if(temp < 0){
			temp+=24;
		}
		double UT = hour + .016666666666666666 * minute + .0002777777777777 * second;
		temp += 1.002737909 * UT;
		if(temp < 0){
			temp+=24;
		}
		if(temp > 24){
			temp-=24;
		}*/
    temp = (temp - (((int)(temp/24)) * 24.0));
		return temp;
	}

	/**
	 *
	 * @return the julian Date
	 */
	private double jd(){
		int m = month;
		int y = year;
		if (m <= 2){
			y--;
			m+=12;
		}
		int a = y /100;
		int b = a/4;
		int c = 2-a+b;
		int e = (int)(365.25*(y + 4716));
		int f = (int)(30.6001 * (m + 1));
		return c+day+e+f- 1524.5;
	}

	/**
	 *
	 */
	private void updateSiderealTime(){
        double gst = GST();
        Shour = (int)(gst);
        double remainder = (gst - Shour)* 60;
        Sminute = (int)(remainder);
        Ssecond = (int)((remainder - Sminute) * 60);
    }

	/**
	 *
	 */
	public void update(){
        TimeZone tz = calendar.getTimeZone();
        int hourtemp = calendar.get(Calendar.HOUR_OF_DAY);
        int daytemp = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeZone(TimeZone.getTimeZone("Etc/GMT-0"));
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH ) +1;
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        calendar.setTimeZone(tz);
        calendar.set(Calendar.HOUR_OF_DAY,hourtemp);
        calendar.set(Calendar.DAY_OF_MONTH,daytemp);
        updateSiderealTime();
    }

	/**
	 *
	 * @param lyear
	 */
	public void setYear(int lyear){
    	calendar.set(Calendar.YEAR,lyear);
    }

	/**
	 *
	 * @param lmonth
	 */
	public void setMonth(int lmonth){
		calendar.set(Calendar.MONTH,lmonth);
    }

	/**
	 *
	 * @param ldate
	 */
	public void setDate(int ldate){
    	calendar.set(Calendar.DAY_OF_MONTH, ldate);
    }

	/**
	 *
	 * @param lhour
	 */
	public void setHour(int lhour){
    	calendar.set(Calendar.HOUR_OF_DAY, lhour);
	}

	/**
	 *
	 * @param lminute
	 */
	public void setMinute(int lminute){
    	calendar.set(Calendar.MINUTE, lminute);
    }

	/**
	 *
	 * @param lsecond
	 */
	public void setSecond(int lsecond){
    	calendar.set(Calendar.SECOND, lsecond);
    }

	/**
	 *
	 */
	public void now(){
    	calendar = new GregorianCalendar();
    	update();
	}

	public int getYear(){
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth(){
		return calendar.get(Calendar.MONTH);
	}

	public int getDay(){
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getHour(){
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public int getMinute(){
		return calendar.get(Calendar.MINUTE);
	}

	public int getSecond(){
		return calendar.get(Calendar.SECOND);
	}

	public double getSiderealTime(){
		return Shour + (Sminute / 60.0) + (Ssecond / 3600.0);
	}

}
