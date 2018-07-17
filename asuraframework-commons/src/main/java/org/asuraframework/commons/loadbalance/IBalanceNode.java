/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.loadbalance;

/**
 * <p>参与负载均衡node</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/14 下午5:01
 * @since 1.0
 */
public interface IBalanceNode {

    /**
     * 获取节点的权重，例如:轮询算法可以需要根据权重调度
     *
     * @return
     */
    int getWeight();

    /**
     * 获取node节点的名称,作为Hash使用
     * 节点名称必须唯一
     *
     * @return
     */
    String getUniqueNodeName();
}
