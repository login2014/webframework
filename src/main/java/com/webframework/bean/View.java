package com.webframework.bean;

import java.util.Map;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/28
 * @Since 1.0.0
 * @Descript 视图展现层
 */
public class View {

//    视图路径
    private String path;
//    模板数据
    private Map<String, Object> model;

    public View addModel(String key, Object value){
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
