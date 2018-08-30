package com.yuheng.wang.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class HelloInterceptor {
    private static Logger log = LoggerFactory.getLogger(HelloInterceptor.class);

    public void doBefore() {
        log.info(">>>Enter HelloInterceptor doBefore method...");
    }

    public void doAfter() {
        log.info(">>>Enter HelloInterceptor doAfter method...");
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        log.info(">>>Enter HelloInterceptor doAround before method...");
        Object obj = pjp.proceed();
        log.info("Object:" + obj);
        log.info(">>>Enter HelloInterceptor doAround after method...");
        return obj;
    }

    public void doReturn() {
        log.info(">>>Enter HelloInterceptor doReturn method...");
    }

    public void doThrowing() {
        log.info(">>>Enter HelloInterceptor doThrowing method...");
    }
}
