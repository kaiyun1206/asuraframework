/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.exception;

/**
 * <p>系统异常:在异常报警体系需要报警</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/19 上午10:09
 * @since 1.0
 */
public class SystemException extends BaseException {

    private static final long serialVersionUID = -334310277941331941L;

    /**
     * 默认业务异常code 前三位表示系统，中间三位表示功能，末尾三位表示错误原因
     */
    private static final int DEFAULT_SYSTEM_CODE = 999999999;

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public SystemException(String message, Throwable cause) {
        super(DEFAULT_SYSTEM_CODE, message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public SystemException(String message) {
        super(DEFAULT_SYSTEM_CODE, message);
    }

    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public SystemException(Throwable cause) {
        super(DEFAULT_SYSTEM_CODE, "", cause);
    }
}
