package utils;

import models.Bus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

//预约时间检验
public class CheckTime {

    public static boolean checkTime(Bus bus) {
        Date d = new Date();
        String time = dateToString(d, "HH:mm");

        StringTokenizer st1 = new StringTokenizer(time, ":");
        int nh = Integer.parseInt(st1.nextToken());
        int nm = Integer.parseInt(st1.nextToken());
        String nw = getWeekOfDate(d);

        StringTokenizer st2 = new StringTokenizer(bus.getDdl(), ":");
        int sh = Integer.parseInt(st2.nextToken());
        int sm = Integer.parseInt(st2.nextToken());
        String sw = bus.getServeDate();

        if (!nw.equals(sw)) {
            return false;
        } else {
            if (nh >sh) {
                return false;
            } else if (nm > sm) {
                return false;
            }

            return true;
        }


    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        Calendar cal = Calendar.getInstance();

        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if (w < 0)

            w = 0;

        return weekDays[w];

    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(date);
        return s;
    }
}
