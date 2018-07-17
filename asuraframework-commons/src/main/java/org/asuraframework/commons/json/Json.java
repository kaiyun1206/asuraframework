/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.json;

import org.asuraframework.commons.utils.Check;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Json 工具</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午5:35
 * @since 1.0
 */
public class Json {

    /**
     * 定义存储JSON的map
     */
    private static ConcurrentHashMap<JsonStyle, IJson> jsonMap = new ConcurrentHashMap<>(JsonStyle.values().length);

    static {
        for (JsonStyle jsonStyle : JsonStyle.values()) {
            IJson json = jsonMap.get(jsonStyle);
            if (Check.isNull(json)) {
                switch (jsonStyle) {
                    case JackJson:
                        json = JackJsonFormat.getInstance();
                        jsonMap.put(jsonStyle, json);
                        break;
                }
            }
        }
        if (Check.isNullOrEmpty(jsonMap)) {
            jsonMap.put(JsonStyle.JackJson, JackJsonFormat.getInstance());
        }
    }

    private Json() {
    }

    /**
     * 获取JSON实例
     *
     * @param jsonStyle
     *
     * @return
     */
    public static IJson getJson(JsonStyle jsonStyle) {
        IJson json = jsonMap.get(jsonStyle);
        if (Check.isNull(json)) {
            json = jsonMap.get(JsonStyle.JackJson);
        }
        return json;
    }

    public static IJson getJackJson() {
        return getJson(JsonStyle.JackJson);
    }

    public static IJson getJson() {
        return getJson(JsonStyle.JackJson);
    }

    /**
     * 定义支持JSON组件
     */
    public enum JsonStyle {
        JackJson, FastJson
    }
}
