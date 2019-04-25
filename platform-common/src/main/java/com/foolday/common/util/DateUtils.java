package com.foolday.common.util;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 时间格式化tools
 */
public final class DateUtils {
    private static final List<String> datePattern = Lists.newArrayList();

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd hh:mm:ss";
    public static final String yyyyMMdd = "yyyyMMdd";

    static {
        datePattern.add(yyyy_MM_dd_HH_mm_ss);
        datePattern.add(yyyy_MM_dd_hh_mm_ss);
        datePattern.add(yyyyMMdd);
    }


    private DateUtils() {
    }

    public static String ofStandardDate(@NotNull Date date) {
        PlatformAssert.notNull(date, "时间不可为空！");
        return DateFormatUtils.format(date, yyyy_MM_dd_HH_mm_ss);
    }

    public static String ofStandardLocalDateTime(@NotNull LocalDateTime date) {
        return fromLocalDateTime(date, yyyy_MM_dd_HH_mm_ss);
    }

    public static String ofyyyyMMdd(@NonNull LocalDateTime dateTime) {
        return fromLocalDateTime(dateTime, yyyyMMdd);
    }

    private static String fromLocalDateTime(@NotNull LocalDateTime date, String pattern) {
        PlatformAssert.notNull(date, "时间不可为空！");
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
}
