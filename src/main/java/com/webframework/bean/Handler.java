package com.webframework.bean;

import java.lang.reflect.Method;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/27
 * @Since 1.0.0
 * @Descript 处理器用于封装被Controller注释的类型，包括controller类型和被Action注释的方法
 */
public class Handler {

//    保存controller类型
    private Class<?> controllerClass;
//    保存被Action注释的方法
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod){
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
