package org.extvos.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Mingcai SHEN
 */
public class Network {

    /**
     * 获取当前机器的IP
     *
     * @return /
     */
    public static String getLocalIp() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return "unknown";
        }
        byte[] ipAddr = addr.getAddress();
        StringBuilder ipAddrStr = new StringBuilder();
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr.append(".");
            }
            ipAddrStr.append(ipAddr[i] & 0xFF);
        }
        return ipAddrStr.toString();
    }
}
