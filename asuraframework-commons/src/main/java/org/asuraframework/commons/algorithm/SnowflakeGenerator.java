/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.algorithm;

/**
 * <p>
 * snowflake 雪花模型算法，参考twitter https://github.com/twitter/snowflake 实现
 * 核心思想：将一个64位的二进制数据，进行划分，例子如下
 * --|---------------41位时间戳----------------|              |-12位序列号-|
 * 0 00000000000000000000000000000000000000000   0000000000  000000000000
 * |                                            |10位机器编码|
 * 第一位弃用 第一位为符号位，（正数为0）不可变
 * 限制：按照上面例子
 * 由于时间戳位数有限，有容量限制，例如41位最多可以容纳69年的数据生成，2199023255551毫秒
 * 由于10位机器编码，最多支持1023个机器节点同时生成
 * 由于12位序列号，最多支持每毫秒生成4095个序列号
 * sonwflake 缺点：
 * 如果集群机器时间不统一，在单机上是递增的，但是由于涉及到分布式环境，
 * 每台机器上的时钟不可能完全同步，也许有时候也会出现不是全局递增的情况
 * </p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/8 下午1:46
 * @since 1.0
 */
public class SnowflakeGenerator {

    /**
     * 定位时间点 2016-01-01 00:00:00.000
     */
    public static final Long POSITION_TIME = 1451577600000L;

    /**
     * 生成ID的最大位数
     */
    public static final long ID_MAX_BITS = 63L;

    /**
     * 时间戳占用最大位数
     */
    private static final int ID_MAX_TIMESTAMP_BITS = 41;

    /**
     * 机器标识占用最大位数
     */
    private static final int ID_MAX_WORKER_BITS = 10;

    /**
     * 序列号占用最大位数
     */
    private static final int ID_MAX_SEQUENCE_BITS = 12;

    /**
     * 最大时间偏移量 （当前时间 - POSITION_TIME） 最多支持69年
     */
    private long maxMillisOffset;

    /**
     * 机器标识，最多可占用10位 1111111111 最多 2^10-1=1023 个机器集群
     */
    private long maxWorkerId;

    /**
     * 序列号占12位 111111111111 最大 2^12-1 = 4095 支持最多每个机器每毫秒4095个ID
     * 0 00000000000000000000000000000000000000000   0000000000  111111111111
     */
    private long maxSequence;

    /**
     * 时间值向左移sequenceBits+workerBits位
     */
    private long timestampLeftOffset;

    /**
     * 机器标识值向左移sequenceBits位
     */
    private int workerLeftOffset;

    /**
     * 初始化开始时间
     */
    private long lastTimestamp = -1L;

    /**
     * 机器标识，最多可占用10位 1111111111 最多 2^10-1=1023 个机器集群
     */
    private int workerId;

    /**
     * 初始化sequence
     */
    private long sequence = 0;

    /**
     * 只有一个机器标识参数的构造器
     *
     * @param workerId
     */
    public SnowflakeGenerator(int workerId) {
        this(ID_MAX_TIMESTAMP_BITS, ID_MAX_WORKER_BITS, ID_MAX_SEQUENCE_BITS, workerId);
    }

    public SnowflakeGenerator(int timestampBits, int workerBits, int sequenceBits, int workerId) {
        if (workerBits < 1 || sequenceBits < 1 || timestampBits < 1) {
            throw new IllegalArgumentException(String.format("timestampBits, workerBits, sequenceBits cannot less than 0"));
        }
        this.workerLeftOffset = sequenceBits;
        this.workerId = workerId;
        this.timestampLeftOffset = workerBits + sequenceBits;
        int maxBits = timestampBits + workerBits + sequenceBits;
        if (maxBits > ID_MAX_BITS) {
            throw new IllegalArgumentException(String.format("timestampBits + workerBits + sequenceBits greater than %d or less than 0", 63));
        }
        this.maxSequence = -1L ^ (-1L << sequenceBits);
        this.maxWorkerId = -1L ^ (-1L << workerBits);
        this.maxMillisOffset = -1L ^ (-1L << timestampBits);
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException(String.format("workId cannot be greater than %d or less than 0", maxWorkerId));
        }
    }

    public long nextId() {
        // 获取到当前的时间
        Long current = timeGen();
        if ((current - POSITION_TIME) > maxMillisOffset) {
            throw new RuntimeException(String.format("Time over flow. Refusing to generate id for time %d ", current));
        }
        // 时钟倒置错误
        if (current < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - current));
        }
        // 如果当前时间相等，则获取下一个序列号
        if (current == lastTimestamp) {
            // 判断sequence是否溢出，如果溢出，则等下一毫秒的到来
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                current = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        //检查当前时间是否溢出，一旦溢出
        if ((current - POSITION_TIME) > maxMillisOffset) {
            throw new RuntimeException(String.format("Time over flow. Refusing to generate id for time %d ", current));
        }
        lastTimestamp = current;
        return ((current - POSITION_TIME) << timestampLeftOffset) | (workerId << workerLeftOffset) | sequence;
    }

    /**
     * 等待到下一秒
     *
     * @param lastTimestamp
     *
     * @return
     */
    private Long tilNextMillis(long lastTimestamp) {
        long current = timeGen();
        while (current <= lastTimestamp) {
            current = timeGen();
        }
        return current;
    }

    /**
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
