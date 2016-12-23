package com.webframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Author ºéÉÙ´Ï ¡¾312451021@qq.com¡¿
 * @Date 2016/11/24 21:09
 * @Since 1.0.0
 * @Descript ¿ØÖÆÆ÷×¢½â
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
