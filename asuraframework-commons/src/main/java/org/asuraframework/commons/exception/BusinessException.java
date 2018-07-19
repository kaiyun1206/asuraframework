/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.exception;

/**
 * <p>业务异常基类：业务异常，有的需要监控</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/19 上午10:06
 * @since 1.0
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 4377208638303574586L;

    /**
     * 默认业务异常code 前三位表示系统，中间三位表示功能，末尾三位表示错误原因
     */
    private static final int DEFAULT_BUSINESS_CODE = 998999999;

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public BusinessException(String message, Throwable cause) {
        super(DEFAULT_BUSINESS_CODE, message, cause);
    }

    /**
     * 构造器
     *
     * @param code    异常错误编码
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public BusinessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }


    /**
     * 构造器
     *
     * @param code    异常错误编码
     * @param message 异常详细信息
     */
    public BusinessException(int code, String message) {
        super(code, message);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public BusinessException(String message) {
        super(DEFAULT_BUSINESS_CODE, message);
    }
}
