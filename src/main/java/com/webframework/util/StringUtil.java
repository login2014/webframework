package com.webframework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/26 21:45
 * @Since 1.0.0
 * @Descript 字符串操作工具类
 */

public class StringUtil {
    public static boolean isNotEmpty(String strValue) {
        return !isEmpty(strValue);
    }

    private static boolean isEmpty(String strValue) {
        if (strValue != null) {
            strValue = strValue.trim();
        }
        return StringUtils.isEmpty(strValue);
    }

    public static String[] splitString(String body, String s) {
        return body.split(s);
    }
}
