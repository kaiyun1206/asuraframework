/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.exception;

/**
 * <p>加解密异常</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午4:16
 * @since 1.0
 */
public class AsuraCryptException extends RuntimeException {

    private static final long serialVersionUID = 5831641858561540063L;

    /**
     * 构造器
     */
    public AsuraCryptException() {
        super();
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public AsuraCryptException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public AsuraCryptException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public AsuraCryptException(Throwable cause) {
        super(cause);
    }
}
