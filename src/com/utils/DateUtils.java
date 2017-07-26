package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtils {

    private static Logger logger = Logger.getLogger(DateUtils.class);

    /**
     * 把date对象转换成日期字符串，可以指定格式
     * 
     * @param date
     *        日期对象
     * @param dateFormat
     *        日期格式
     * @return
     */
    public static String fromDateToFormatString(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(dateFormat);
            return format.format(date);
        } catch (Exception e) {
            logger.error("日期转换字符串出错!", e);
            return null;
        }
    }
}
