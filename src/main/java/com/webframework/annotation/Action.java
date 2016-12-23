package com.webframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/24 21:12
 * @Since 1.0.0
 * @Descript 请求类型及路径
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    String value();
}
