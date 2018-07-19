/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.utils;

import org.junit.Assert;
import org.junit.Test;

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
 * @date 2018/7/18 上午11:49
 * @since 1.0
 */
public class T_MoneyUtils {

    @Test
    public void testGet() {
        double d = MoneyUtils.builder().get();
        System.out.println(d);
        Assert.assertEquals(Double.toString(d), "0.0");
    }

    @Test
    public void testFormat01() {
        double d = MoneyUtils.builder().with(3.759695d).round(3).get();
        String d1 = MoneyUtils.builder().with(3.759695d).round(3).formatWithPrefix();
        System.out.println(d + "======" + d1);
        Assert.assertEquals(Double.toString(d), "3.76");
        Assert.assertEquals(d1, "¥3.76");
    }

    @Test
    public void testFormat() {
        double d = MoneyUtils.builder().with(3.759695d).round(3).get();
        String d1 = MoneyUtils.builder().with(3.759695d).round(3).format("$%s元");
        System.out.println(d + "======" + d1);
        Assert.assertEquals(Double.toString(d), "3.76");
        Assert.assertEquals(d1, "$3.76元");
    }

    @Test
    public void testDivide() {
        double d = MoneyUtils.builder().with(10.1d).divide(3.3, 3).get();
        System.out.println(d);
        Assert.assertEquals(Double.toString(d), "3.061");
    }

    @Test
    public void testMultiply() {
        double d = MoneyUtils.builder().with(10.1d).multiply(3.312).round(2).get();
        System.out.println(d);
        Assert.assertEquals(Double.toString(d), "33.45");
    }
}