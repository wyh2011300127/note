package com.yuheng.wang.interceptors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yuheng.wang.model.User;

// 意思是这个类为切面类
@Aspect
// 因为作为切面类需要 Spring 管理起来，所以在初始化时就需要将这个类初始化加入 Spring 的管理
@Component
public class LogInterceptor {
    private static Logger log = LoggerFactory.getLogger(LogInterceptor.class);

    @Pointcut("execution(* com.yuheng.wang.service.imp.UserServiceImp.*(..))")
    public void myMethod() {

    }

    // 切入点的逻辑,execution 切入点语法
    @Before("myMethod()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[LogInterceptor] before advise");
    }

    @After("myMethod()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[LogInterceptor] doAfter advise");
    }

    @Around("myMethod()")
    public User doAround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("[LogInterceptor] doAround advise1");
        Object[] args = pjp.getArgs();
        log.info("获取被切函数的参数：" + args);
        Object proceed = pjp.proceed();
        log.info("获取被切函数的返回值：" + proceed);
        // 强制类型转换
        User user = (User)proceed;
        // 修改被切函数的返回值
        user.setAge(25);

        log.info("[LogInterceptor] doAround advise2");
        // return返回值
        return user;
    }

    @AfterReturning(value = "myMethod()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("doReturn result:" + result);
        log.info("[LogInterceptor] doReturn advise");
    }

    @AfterThrowing("myMethod()")
    public void doThrowing(JoinPoint joinPoint) {
        log.info("[LogInterceptor] doThrowing advise");
    }
}
