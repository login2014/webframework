package com.webframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author ���ٴ� ��312451021@qq.com��
 * @Date 2016/11/24
 * @Since 1.0.0
 * @Descript ����ע��ע��
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

}
