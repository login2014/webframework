package com.webframework.bean;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/28
 * @Since 1.0.0
 * @Descript 封装返回页面的数据，该对象直接写入HttpServletResponse中
 */
public class Data {

//    模型数据
    private Object model;

    public Data(Object model){
        this.model = model;
    }

    public Object getModel(){
        return model;
    }
}
