package com.webframework.helper;

import com.webframework.annotation.Inject;
import com.webframework.util.CollectionUtil;
import com.webframework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/26
 * @Since 1.0.0
 * @Descript 依赖注入类。先通过BeanHelper获取被注解的类集合，而后遍历类获取类中被@Inject注解的属性，
 * 通过ReflectionUtil的setField方法设置注解的值。
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> classObjectMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(classObjectMap.entrySet())){
            for(Map.Entry<Class<?>, Object> classObjectEntry : classObjectMap.entrySet()){
//                获取被注解类型和对象
                Class<?> beanClass = classObjectEntry.getKey();
                Object beanObject = classObjectEntry.getValue();
                for(Field field : beanClass.getFields()){
//                    判断是否有备Inject注解的属性
                    if(field.isAnnotationPresent(Inject.class)){
//                        获取被注解属性的类型后通过BeanHelper返回的对象赋值
                        Class<?> classField = field.getType();
                        Object fieldObject = classObjectMap.get(classField);
                        if(fieldObject != null){
//                            通过ReflectionUtil的设置方法设置
                            ReflectionUtil.setField(beanObject, field, fieldObject);
                        }
                    }
                }
            }
        }
    }
}
