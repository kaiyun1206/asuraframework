/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.date;

import org.asuraframework.commons.utils.Check;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;

/**
 * <p></p>
 * <p>
 * <PR日期格式化E>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午3:19
 * @since 1.0
 */
public class DateFormatter {

    /**
     * 私有化构造
     */
    private DateFormatter() {
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd
     *
     * @param date
     *
     * @return date
     */
    public static String formatDate(@Nullable Date date) {
        return format(date, DatePattern.DEFAULT_FORMAT_DATE_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd
     *
     * @param milliseconds 毫秒
     *
     * @return date
     */
    public static String formatDate(long milliseconds) {
        return format(milliseconds, DatePattern.DEFAULT_FORMAT_DATE_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd HH:mm:ss
     * 24小时制
     *
     * @param date
     *
     * @return
     */
    public static String formatDateTime(@Nullable Date date) {
        return format(date, DatePattern.DEFAULT_FORMAT_DATETIME_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd HH:mm:ss
     * 24小时制
     *
     * @param milliseconds 毫秒
     *
     * @return
     */
    public static String formatDateTime(long milliseconds) {
        return format(milliseconds, DatePattern.DEFAULT_FORMAT_DATETIME_PATTERN);
    }

    /**
     * 按照指定格式，格式化日期
     * 如果需要格式化的日期为空，返回空字符串
     * <p>
     * <p/>
     *
     * @param date
     * @param pattern
     *
     * @return
     */
    public static String format(@Nullable Date date, @Nonnull String pattern) {
        if (Check.isNull(date)) {
            return "";
        }
        Objects.requireNonNull(pattern, "date pattern must not null");
        return format(date.getTime(), pattern);
    }

    /**
     * 按照指定格式，格式化日期
     * <p/>
     *
     * @param milliseconds 毫秒
     *
     * @return
     */
    public static String format(long milliseconds, @Nonnull String pattern) {
        Objects.requireNonNull(pattern, "date pattern must not null");
        return DateUtils.builder().withDate(milliseconds).format(pattern);
    }
}
