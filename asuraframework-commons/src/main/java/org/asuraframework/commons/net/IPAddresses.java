/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * <p>IP地址处理</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/17 下午6:37
 * @since 1.0
 */
public class IPAddresses {

    public static final String LOCALHOST = "127.0.0.1";

    public static final String ANYHOST = "0.0.0.0";

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private IPAddresses() {

    }

    /**
     * 验证是不是有效的ip地址
     *
     * @param address 待验证IP字符串
     *
     * @return
     */
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null && !ANYHOST.equals(name) && !LOCALHOST.equals(name) && IP_PATTERN.matcher(name).matches());
    }


    /**
     * 获取本地ip地址
     *
     * @return
     */
    public static InetAddress getLocalAddress() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    NetworkInterface network = interfaces.nextElement();
                    Enumeration<InetAddress> addresses = network.getInetAddresses();
                    if (addresses != null) {
                        while (addresses.hasMoreElements()) {
                            InetAddress address = addresses.nextElement();
                            if (isValidAddress(address)) {
                                return address;
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return localAddress;
    }
}
