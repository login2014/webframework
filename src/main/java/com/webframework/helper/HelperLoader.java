package com.webframework.helper;

import com.webframework.util.ClassUtil;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/27
 * @Since 1.0.0
 * @Descript 用于初始化框架中的helper对象
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
//                先获取BasePackage下所有的类
                ClassHelper.class,
//                再将类与类实例化对象保存在Map中
                BeanHelper.class,
//                再将类属性中Inject注解的属性注入值
                IocHelper.class,
//                在扫描被Controller注解的类中被Action注解的方法也保存到Map中
                ControllerHelper.class};
        for (Class<?> clazz : classList){
            ClassUtil.loadClass(clazz.getName());
        }
    }
}
