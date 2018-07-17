/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.json;

import org.junit.Test;

import static org.junit.Assert.*;

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
 * @date 2018/7/16 下午5:59
 * @since 1.0
 */
public class T_Json {

    @Test
    public void getJackJson() {
        String json = "{\"age\":123}";
        Integer age = Json.getJackJson().getInt(json, "age");
        System.out.println(age);
    }
}