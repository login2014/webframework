package com.webframework.helper;

import com.webframework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/26
 * @Since 1.0.0
 * @Descript 通过类辅助类来获取被注释的类型集合，而后将所有类型对象实例化保存到Map中
 */
public class BeanHelper {

    private static final Logger logger = LoggerFactory.getLogger(BeanHelper.class);

    private static Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz : classSet) {
            Object object = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, object);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz)){
            logger.error("get bean failure" + clazz);
            throw new RuntimeException("get bean failure" + clazz);
        }
        return (T) BEAN_MAP.get(clazz);
    }
}
