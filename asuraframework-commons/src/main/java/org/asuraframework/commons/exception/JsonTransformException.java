/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.exception;

/**
 * <p>Json转换异常</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午3:53
 * @since 1.0
 */
public class JsonTransformException extends SystemException {

    private static final long serialVersionUID = 2826302733345457336L;

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public JsonTransformException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public JsonTransformException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public JsonTransformException(Throwable cause) {
        super(cause);
    }
}
