package com.example.administrator.sharenebulaproject.widget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CalendarBuilder {

    private String TAG = getClass().getSimpleName();
    private Calendar calendar;
    private String year;
    private String month;
    private String day;
    private int hour;
    private int minute;

    public CalendarBuilder(Calendar calendar) {
        this.calendar = calendar;
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    public String queryYear() {
        year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    public String queryMonth() {
        month = String.valueOf((calendar.get(Calendar.MONTH)) + 1);
        return month;
    }

    public String queryDay() {
        day = String.valueOf(calendar.get(Calendar.DATE));
        return day;
    }

    public int queryHour() {
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public int queryMinute() {
        minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    public static long getFormatLongDate(String time) {
        long longTime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date utilDate = sdf.parse(time);
            longTime = utilDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    public static long getFormatLongMinDate(String time) {
        long longTime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date utilDate = sdf.parse(time);
            longTime = utilDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    public static String getTimeDifference(long beginTime, long endTime) {
        Long between = (beginTime - endTime) / 1000;// 除以1000是为了转换成秒
        String day = ((Long) (between / (24 * 3600))).toString();
        String hour = ((Long) (between % (24 * 3600) / 3600)).toString();
        String minute = ((Long) (between % 3600 / 60)).toString();
        String second = ((Long) (between % 60 / 60)).toString();

        if (hour.length() == 1) {
            hour = "0" + hour.toString();
        }
        if (minute.length() == 1) {
            minute = "0" + minute.toString();
        }
        if (second.length() == 1) {
            second = "0" + second.toString();
        }
        if (day.equals("0")) {
            int time;
            if (Math.abs(Integer.valueOf(minute)) == 0){
                time = 1;
            }else {
                time =  Math.abs(Integer.valueOf(minute));
            }
            return new StringBuffer().append(time).append("分钟").toString(); //Math.abs(Integer.valueOf(hour)) + "小时"
        } else {
            return Math.abs(Integer.valueOf(day)) + "天  " + Math.abs(Integer.valueOf(hour)) + "小时" + Math.abs(Integer.valueOf(minute)) + "分钟";// + Math.abs(Integer.valueOf(second))
        }
    }

    public static int getAgeByBirthday(String birthday) throws ParseException {
        // 格式化传入的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = format.parse(birthday);
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date()); // 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(parse); // 传入的时间

            //如果传入的时间，在当前时间的后面，返回0岁
            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {
            return 0;
        }
    }

}
