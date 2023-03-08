package com.hugqq.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {
    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        /*
          @link {org.apache.catalina.connector.Request}
         */
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost1 = "127.0.0.1";
        String localhost2 = "0:0:0:0:0:0:0:1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost1.equals(ip) || localhost2.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return "127.0.0.1";
            }
        }
        return ip;
    }
}
