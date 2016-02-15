package com.zhuangjy.framework.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Author:王旗
 * Date:2015/6/24 16:15
 * Description:
 */
public class Util {

    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    public static HttpSession getSession() {
        return getRequest()==null?null:getRequest().getSession();
    }

    public static String getPrincipal() {
        return getRequest() == null ? null : getRequest().getHeader("principal");
    }

    public static String getLoginIp() {
        return getRequest() == null ? null : getRequest().getHeader("loginIp");
    }

    public static boolean isAdmin() {
        return getRequest() == null ? null : (StringUtils.hasText(getRequest().getHeader("admin")) ? getRequest().getHeader("admin").equalsIgnoreCase("true") : false);
    }

    public static String getMap(Map map, String str) {
        return getMap(map, str, "");
    }

    /**
     * 从map获取字符串
     */
    public static String getMap(Map map, String str, String dv) {
        if (str == null)
            return dv;
        str = str.trim();
        if (str.length() == 0)
            return dv;
        if (map == null)
            return dv;
        if (map.containsKey(str) == false)
            return dv;
        if (map.containsKey(str) == false)
            return dv;
        String s = "" + map.get(str);
        s = s.trim();
        if (s.length() == 0) {
            return dv;
        }
        return s;
    }

}
