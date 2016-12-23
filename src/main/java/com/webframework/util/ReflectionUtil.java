package com.webframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author ���ٴ� ��312451021@qq.com��
 * @Date 2016/11/26
 * @Since 1.0.0
 * @Descript ͨ��java����ԭ�������ʵ�����󣬵��÷��������ö�������
 */
public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> clazz){
        Object instance = null;
        try {
            clazz.newInstance();
        } catch (Exception e) {
            logger.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return  instance;
    }

    /**
     * ���ö��󷽷�
     * @param obj
     * @param method
     * @param args ��������
     * @return
     */
    public static Object invokeMethod(Object obj, Method method, Object args){
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * ��������ֵ
     * @param obj ����
     * @param field ����
     * @param value ֵ
     */
    public static void setField(Object obj, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            logger.error("set field failture", e);
            throw new RuntimeException(e);
        }
    }
}
