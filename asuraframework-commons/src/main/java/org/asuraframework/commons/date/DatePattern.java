/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.date;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午2:56
 * @since 1.0
 */
public class DatePattern {

    /**
     * 私有化构造
     */
    private DatePattern() {

    }

    /**
     * 日期 24小时制
     */
    public static final String DEFAULT_FORMAT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间 24小时制
     */
    public static final String DEFAULT_FORMAT_HOUR_PATTERN = "yyyy-MM-dd HH";

    /**
     * 分钟
     */
    public static final String DEFAULT_FORMAT_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 秒
     */
    public static final String DEFAULT_FORMAT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 毫秒
     */
    public static final String DEFAULT_FORMAT_MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.sss";

    /**
     * ISO 国际通行表示法
     */
    public static final String DEFAULT_ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * 秒
     */
    public static final String DEFAULT_FORMAT_DATETIME_PATTERN_LONG = "yyyyMMddHHmmss";

    /**
     * 毫秒
     */
    public static final String DEFAULT_FORMAT_MILLISECOND_PATTERN_LONG = "yyyyMMddHHmmsssss";
}
