package com.webframework.bean;

import com.webframework.util.CastUtil;

import java.util.Map;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/27
 * @Since 1.0.0
 * @Descript 封装参数
 */
public class Param {

    private Map<String, Object> paramMap;
    public Param(Map<String, Object> paramMap){
        this.paramMap = paramMap;
    }

//    根据参数名转换为Long类型
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
