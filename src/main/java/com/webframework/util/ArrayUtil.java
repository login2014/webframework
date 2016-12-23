package com.webframework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/27
 * @Since 1.0.0
 * @Descript 数组工具类
 */
public class ArrayUtil {

    /**
     * 判断是否为空
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object [] array){
        return !ArrayUtils.isEmpty(array);
    }

    /**
     * 判断为空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object [] array){
        return ArrayUtils.isEmpty(array);
    }
}
