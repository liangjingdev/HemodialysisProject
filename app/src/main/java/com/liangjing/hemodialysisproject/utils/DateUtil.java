package com.liangjing.hemodialysisproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liangjing on 2017/8/30.
 * <p>
 * function:将日期转化为毫秒数，将毫秒数转化为日期
 */

public class DateUtil {

    //指定日期格式(统一)
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //将日期转化为毫秒数（年、月、日）--没有精确到'日'级别以下
    public static Long transformMillis(String date) {
        try {
            return simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //将毫秒数转化为对应的日期(年、月、日)
    public static String transformDate(Long millis){
        Date date = new Date();
        date.setTime(millis);
        return simpleDateFormat.format(date);
    }
}
