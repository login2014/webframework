package com.webframework.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/26 21:41
 * @Since 1.0.0
 * @Descript 集合工具类
 */

public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
