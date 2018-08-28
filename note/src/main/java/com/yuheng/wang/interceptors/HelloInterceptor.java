package com.yuheng.wang.interceptors;

public class HelloInterceptor {

    public void doBefore() {
        System.out.println("Enter HelloInterceptor doBefore method...");
    }

    public void doAfter() {
        System.out.println("Enter HelloInterceptor doAfter method...");
    }

    public void doAround() {
        System.out.println("Enter HelloInterceptor doAround method...");
    }

    public void doReturn() {
        System.out.println("Enter HelloInterceptor doReturn method...");
    }

    public void doThrowing() {
        System.out.println("Enter HelloInterceptor doThrowing method...");
    }
}
