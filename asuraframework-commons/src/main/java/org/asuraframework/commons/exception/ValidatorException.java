/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.exception;

/**
 * <p>参数校验异常：在异常报警体系可以忽略</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/19 下午2:38
 * @since 1.0
 */
public class ValidatorException extends BusinessException {

    /**
     * 默认code为 997999999
     */
    public static final int DEFAULT_VALIDATOR_CODE = 997999999;

    private static final long serialVersionUID = -6797121082762066139L;

    /**
     * 构造器
     *
     * @param code    异常错误编码
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public ValidatorException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public ValidatorException(String message, Throwable cause) {
        super(DEFAULT_VALIDATOR_CODE, message, cause);
    }

    /**
     * 构造器
     *
     * @param code    异常错误编码
     * @param message 异常详细信息
     */
    public ValidatorException(int code, String message) {
        super(code, message);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public ValidatorException(String message) {
        super(DEFAULT_VALIDATOR_CODE, message);
    }
}
