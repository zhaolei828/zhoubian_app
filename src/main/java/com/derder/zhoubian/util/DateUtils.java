package com.derder.zhoubian.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;

	public static Date string2Date(String dateStr,String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(dateStr);
		return date;
	}
	public static String date2String(Date date , String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateStr = sdf.format(date);
		return dateStr;
	}

    /**
     * 距离今天多久
     * @param date
     * @return
     *
     */
    public static String fromToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Date now = new Date();
        long paramTime = date.getTime() / 1000;
        long nowTime = now.getTime() / 1000;
        long ago = nowTime - paramTime;
        if (ago < ONE_HOUR) {
            return ago / ONE_MINUTE + "分钟前";
        } else {
            long todayZeroTime = getTodayZeroTime();
            long yesDayZeroTime = todayZeroTime - 24*60*60*1000l;
            long tomDayZeroTime = todayZeroTime + 24*60*60*1000l;
            //如果是今天，显示今天几点几分
            if(todayZeroTime < date.getTime() && date.getTime() < tomDayZeroTime){
                return "今天 " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            }
            //如果是昨天，显示昨天几点几分
            else if (yesDayZeroTime < date.getTime() && date.getTime() < todayZeroTime) {
                return "昨天 " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            }
            //其他，显示哪天的几点几分
            else {
                return date2String(date,"yyyy-MM-dd HH:mm");
            }
        }
    }

    /**
     * 获取当天0点的时间
     * @return
     */
    public static long getTodayZeroTime () {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static void main(String[] args) {
        System.out.println(fromToday(new Date(1407293730*1000l)));
    }
}
