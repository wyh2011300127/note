package com.yuheng.wang.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "stringUtil")
public class StringUtil {
    private static Logger log = LoggerFactory.getLogger(StringUtil.class);

    public String getParam(String param) {
        param = param + "Enter StringUtil getParam method ";

        log.info(">>>param:" + param);
        return param;
    }

}
