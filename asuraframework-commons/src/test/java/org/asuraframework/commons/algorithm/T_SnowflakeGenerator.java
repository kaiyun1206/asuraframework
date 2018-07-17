/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.algorithm;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

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
 * @date 2018/7/14 上午9:54
 * @since 1.0
 */
public class T_SnowflakeGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(T_SnowflakeGenerator.class);

    @Test
    public void t_nextId_01() {
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator(41, 10, 12, 1023);
        long nextId = snowflakeGenerator.nextId();
        LOGGER.info("nextId:{}", nextId);
        Assert.assertNotNull(nextId);
    }

    @Test
    public void t_nextId_02() {
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator(1023);
        long nextId = snowflakeGenerator.nextId();
        LOGGER.info("nextId:{}", nextId);
        Assert.assertNotNull(nextId);
    }

    @Test
    public void t_01() {
        System.out.println(Long.toBinaryString(System.currentTimeMillis()));
        System.out.println(new Date(4398046511103L));
        System.out.println(-1L ^ (-1L << 42));
        System.out.println(Integer.toBinaryString(4095));
        System.out.println(Integer.toBinaryString(4096));
        System.out.println(Integer.toBinaryString(4095 & 4096));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(-1L ^ -1L);
        System.out.println(-1L << 10);
        System.out.println(-1L << 10);
        System.out.println(-1L ^ (-1L << 10));
        System.out.println(Long.toBinaryString(-1L));
        System.out.println(Long.toBinaryString(-1L << 10));
        System.out.println(Long.toBinaryString(-1L ^ (-1L << 10)));
        LOGGER.info("{}", 2L ^ 10L - 1);
        System.out.println(Integer.class.getName());
    }
}