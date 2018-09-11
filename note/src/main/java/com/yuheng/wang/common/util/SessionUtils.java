package com.yuheng.wang.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Session工具类
 * 
 * @author wangyuheng
 * @date 2018/09/03
 */
public class SessionUtils {

    /**
     * 设置session失效
     */
    public static void invalidSession() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().invalidate();
        }
    }

    /**
     * 移除所有的session
     */
    public static void removeAllSession() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            // 待添加
            request.getSession().removeAttribute("");
            request.getSession().removeAttribute("");
            request.getSession().removeAttribute("");
        }
    }

    /**
     * 移除指定的session
     * 
     * @param name
     */
    public static void removeSession(String name) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().removeAttribute(name);
        }
    }

    /**
     * 获取session中的Attribute
     * 
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : (T)request.getSession().getAttribute(name);
    }

    /**
     * 设置session的Attribute
     * 
     * @param name
     * @param value
     */
    public static <T> void putSessionAttribute(String name, T value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().setAttribute(name, value);
        }
    }

    /**
     * 获取HttpServletRequest
     * 
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes
            = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return requestAttributes != null ? requestAttributes.getRequest() : null;
    }
}
