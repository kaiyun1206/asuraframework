/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.exception;

/**
 * <p>bean copy异常处理，继承RuntimeException</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午4:01
 * @since 1.0
 */
public class BeanUtilsCopyException extends RuntimeException {

    private static final long serialVersionUID = -5899907603154679349L;

    /**
     * 构造器
     */
    public BeanUtilsCopyException() {
        super();
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public BeanUtilsCopyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public BeanUtilsCopyException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public BeanUtilsCopyException(Throwable cause) {
        super(cause);
    }
}
