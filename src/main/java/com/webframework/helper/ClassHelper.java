package com.webframework.helper;

import com.webframework.annotation.Controller;
import com.webframework.annotation.Service;
import com.webframework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/24
 * @Since 1.0.0
 * @Descript 类的辅助类
 */
public final class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;
    static{
        String basePackage = ConfigHelper.getBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取被@Service注释的类型集合
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : classSet){
            if(cls.isAnnotationPresent(Service.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取被@Controller注释的类型集合
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls : classSet){
            if(cls.isAnnotationPresent(Controller.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取所有被注释的bean的类型集合
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
}
