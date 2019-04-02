package com.foolday.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DateUtils {
    public static String formatStandard(@NotNull Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }
}
