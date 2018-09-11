package com.yuheng.wang.controller.base;

import javax.servlet.http.HttpServletRequest;

import com.yuheng.wang.common.util.StringUtils;

public class BaseController {
    /**
     * 获取参数值
     * 
     * @param request
     * @param name 参数名
     * @return
     */
    protected String getParameter(String name, HttpServletRequest request) {
        if (name == null) {
            return "";
        }

        String value = request.getParameter(name);
        if (value == null) {
            return null;
        }
        return StringUtils.proccessInjection(value.trim());
    }
}
