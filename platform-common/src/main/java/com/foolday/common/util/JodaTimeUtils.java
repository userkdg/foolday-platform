package com.foolday.common.util;

import lombok.experimental.UtilityClass;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * 此工具类围绕jodatime展开
 */
@UtilityClass
public class JodaTimeUtils {

    /**
     * 时间转字符串
     * @param date
     * @param pattern
     * @return
     */
    public String time2Str(Date date,String pattern){
        DateTime datetime = new DateTime(date);
        return time2Str(datetime,pattern);
    }

    public String time2Str(DateTime dateTime,String pattern){
        return dateTime.toString(pattern);
    }

}
