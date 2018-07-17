/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * <p>配置文件加载</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/17 下午6:32
 * @since 1.0
 */
public class PropertiesUtils {

    private static final String PROPERTIES_FILE_EXTENSION = ".properties";

    private static final String XML_FILE_EXTENSION = ".xml";

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

    private final Properties properties;

    public PropertiesUtils(String... resourcesPaths) {
        properties = loadProperties(resourcesPaths);
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 取出Property，但以System的Property优先,取不到返回空字符串.
     */
    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return "";
    }

    /**
     * 获取所有属性的键值对
     *
     * @return
     */
    public Iterator<Map.Entry<Object, Object>> getAllProperties() {
        return properties.entrySet().iterator();
    }

    /**
     * 获取所有属性名称
     *
     * @return
     */
    public Enumeration<?> propertyNames() {
        return properties.propertyNames();
    }

    /**
     * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
     */
    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    /**
     * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
     */
    public String getProperty(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     */
    public Integer getInteger(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     */
    public Double getDouble(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Double.valueOf(value);
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     */
    public Double getDouble(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
     */
    public Boolean getBoolean(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(value);
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }

    /**
     * 载入多个文件, 文件路径使用 Resource格式.
     */
    private Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();

        for (String location : resourcesPaths) {
            InputStream is = null;
            try {
                if (!location.endsWith(PROPERTIES_FILE_EXTENSION) && !location.endsWith(XML_FILE_EXTENSION)) {
                    location = location + PROPERTIES_FILE_EXTENSION;
                }
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
                props.load(is);
            } catch (IOException ex) {
                LOGGER.info("Could not load properties from path:" + location + ", " + ex.getMessage());
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    LOGGER.info("close exception properties from path:" + location + ", " + e.getMessage());
                }
            }
        }
        return props;
    }
}
