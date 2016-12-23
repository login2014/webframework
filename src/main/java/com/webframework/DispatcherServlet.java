package com.webframework;

import com.webframework.bean.Data;
import com.webframework.bean.Handler;
import com.webframework.bean.Param;
import com.webframework.bean.View;
import com.webframework.helper.BeanHelper;
import com.webframework.helper.ConfigHelper;
import com.webframework.helper.ControllerHelper;
import com.webframework.helper.HelperLoader;
import com.webframework.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/28
 * @Since 1.0.0
 * @Descript 用于分发请求
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
//        初始化相关helper类
        HelperLoader.init();
//        用于注册Servlet
        ServletContext servletContext = getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
//        注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        获取请求方法和路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
//        获取请求处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if(handler != null){
//            获取Controller类型及Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerObj = BeanHelper.getBean(controllerClass);
//            封装请求的参数到Map<String, Object>中
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> enumeration = req.getParameterNames();
            while (enumeration.hasMoreElements()){
                String paramName = enumeration.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if(StringUtil.isNotEmpty(body)){
                String [] params = StringUtil.splitString(body, "&");
                if(ArrayUtil.isNotEmpty(params)){
                    for(String param : params){
                        String [] array = StringUtil.splitString(param, "=");
                        if(ArrayUtil.isNotEmpty(array) && array.length == 2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
//            调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerObj, actionMethod, param);
            if(result instanceof View){
                View view = (View) result;
                String path = view.getPath();
                if(StringUtil.isNotEmpty(path)){
                    if(path.startsWith("/")){
                        res.sendRedirect(req.getContextPath() + path);
                    }else{
                        Map<String, Object> model = view.getModel();
                        for(Map.Entry<String, Object> entry : model.entrySet()){
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppAssetPath() + path).forward(req, res);
                    }
                }else if(result instanceof Data){
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if(model != null){
                        res.setContentType("application/json");
                        res.setCharacterEncoding("UTF-8");
                        PrintWriter writer = res.getWriter();
                        String json = JsonUtil.toJson(model);
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    }
                }
            }
        }
    }
}
