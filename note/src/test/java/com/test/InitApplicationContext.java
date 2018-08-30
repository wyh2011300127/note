package com.test;

import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yuheng.wang.interceptors.HelloInterceptor;

public class InitApplicationContext {
    private final static String PAHT = "spring-mybatis.xml";
    private static ApplicationContext ac;
    static {
        ac = new ClassPathXmlApplicationContext(PAHT);
    }

    @Test
    public void test1() {
        PropertyPlaceholderConfigurer prop = ac.getBean("propertyConfigurer", PropertyPlaceholderConfigurer.class);
        System.out.println(prop.toString());
    }

    @Test
    public void test2() {
        ComboPooledDataSource dataSource = ac.getBean("dataSource", ComboPooledDataSource.class);
        System.out.println(dataSource.getUser() + ":" + dataSource.getPassword());
    }

    @Test
    public void test3() {
        HelloInterceptor helloInterceptor = ac.getBean("helloIntercetor", HelloInterceptor.class);
        System.out.println(helloInterceptor);
    }

    @Test
    public void test4() {
        Object bean = ac.getBean("helloPointCut");
        System.out.println(bean);
    }

}
