/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.exception;

/**
 * <p>异常基类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/19 上午9:55
 * @since 1.0
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = -6954038318406256252L;

    /**
     * 错误码
     */
    private int code;

    /**
     * 构造器
     */
    public BaseException() {
        super();
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     *
     * @Param code 异常编码
     */
    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public BaseException(String message) {
        super(message);
    }


    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }


    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
