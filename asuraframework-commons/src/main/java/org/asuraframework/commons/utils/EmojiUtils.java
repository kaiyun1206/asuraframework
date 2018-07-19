/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.utils;

/**
 * <p>emoji 表情处理</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/18 上午10:34
 * @since 1.0
 */
public class EmojiUtils {



    /**
     * 把emoji直接过滤掉
     *
     * @param input
     *
     * @return
     */
    public static String eraseEmojis(String input) {
        if (Check.isNullOrEmpty(input)) {
            return input;
        }
        return input.replaceAll("[^\\u0000-\\uFFFF]", "");
    }
}
