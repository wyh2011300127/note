package com.yuheng.wang.model;

/**
 * 用户实体类
 * 
 * @author wangyuheng
 * @date 2018/08/28
 */
public class User {
    /** id唯一标识 **/
    private Integer id;
    /** 用户名 **/
    private String name;
    /** 密码 **/
    private String password;
    /** 年龄 **/
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + ", age=" + age + "]";
    }

}
