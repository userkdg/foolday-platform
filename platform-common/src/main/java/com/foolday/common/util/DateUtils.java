package com.foolday.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class DateUtils {
    private static final List<String> datePattern = Lists.newArrayList();

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd hh:mm:ss";

    static {
        datePattern.add(yyyy_MM_dd_HH_mm_ss);
        datePattern.add(yyyy_MM_dd_hh_mm_ss);
    }


    private DateUtils() {
    }

    public static String formatStandard(@NotNull Date date) {
        return DateFormatUtils.format(date, yyyy_MM_dd_HH_mm_ss);
    }

}
