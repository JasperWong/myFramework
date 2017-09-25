package com.jasper.framework;

import com.jasper.framework.bean.Data;
import com.jasper.framework.bean.Handler;
import com.jasper.framework.bean.Param;
import com.jasper.framework.bean.View;
import com.jasper.framework.helper.*;
import com.jasper.framework.util.JsonUtil;
import com.jasper.framework.util.ReflectionUtil;
import com.jasper.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
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
 * Created by JasperWong on 2017-07-17.
 */
@WebServlet(urlPatterns ="/*",loadOnStartup=0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException{
        HelperLoader.init();
        ServletContext servletContext=servletConfig.getServletContext();
        ServletRegistration jspServlet=servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");

        ServletRegistration defaultServlet=servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String requestMethod=request.getMethod().toLowerCase();
        String requestPath=request.getPathInfo();

        if(requestPath.equals("/favicon.ico")){                 //add
            return;
        }

        Handler handler= ControllerHelper.getHandler(requestMethod,requestPath);

        if(handler!=null){
            Class<?> controllerClass=handler.getControllerClass();
            Object controllerBean= BeanHelper.getBean(controllerClass);

            Param param;

            if (UploadHelper.isMultipart(request)) {
                param=UploadHelper.createParam(request);
            }else {
                param= RequestHelper.createParam(request);
            }

            Map<String,Object> paramMap=new HashMap<String,Object>();
            Enumeration<String> paramNames=request.getParameterNames();

//            while(paramNames.hasMoreElements()){
//                String paramName=paramNames.nextElement();
//                String paramValue=request.getParameter(paramName);
//                paramMap.put(paramName,paramValue);
//            }


//            String body=CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
//            if(StringUtil.isNotEmpty(body)){
//                String[] params=StringUtil.splitString(body,"&");
//                if(ArrayUtil.isNotEmpty(params)){
//                    for(String param:params){
//                        String[] array=StringUtil.splitString(param,"=");
//                        if(ArrayUtil.isNotEmpty(array)&&array.length==2){
//                            String paramName=array[0];
//                            String paramValue=array[1];
//                            paramMap.put(paramName,paramValue);
//                        }
//                    }
//                }
//            }
            Object result;
//            Param param=new Param(paramMap);
            Method actionMethod=handler.getActionMethod();

            if(param.isEmpty()){
                result=ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            }else{
                result= ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            }

            if(result instanceof View){
                handlerViewResult((View)result,request,response);
            }else if(result instanceof Data){
                handlerDataResult((Data)result,response);
            }
        }

    }

    private void handlerViewResult(View view,HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        String path=view.getPath();
        if(StringUtil.isNotEmpty(path)){
            response.sendRedirect(request.getContextPath()+path);
        }else {
            Map<String,Object> model=view.getModel();
            for(Map.Entry<String,Object> entry:model.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
            request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request,response);
        }
    }

    private void handlerDataResult(Data data,HttpServletResponse response)throws IOException{
        Object model=data.getModel();
        if(model!=null){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer=response.getWriter();
            String json= JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

}
//            if(result instanceof View){
//                View view =(View) result;
//                String path=view.getPath();
//                if(StringUtil.isNotEmpty(path)){
//                    if(path.startsWith("/")){
//                        response.sendRedirect(request.getContextPath()+path);
//                    }else {
//                        Map<String,Object> model=view.getModel();
//                        for(Map.Entry<String,Object> entry:model.entrySet()){
//                            request.setAttribute(entry.getKey(),entry.getValue());
//                        }
//                        request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request,response);
//                    }
//                }
//            } else if(result instanceof Data){
//                Data data=(Data) result;
//                Object model=data.getModel();
//                if(model!=null){
//                    response.setContentType("application/json");
//                    response.setCharacterEncoding("UTF-8");
//                    PrintWriter writer=response.getWriter();
//                    String json= JsonUtil.toJson(model);
//                    writer.write(json);
//                    writer.flush();
//                    writer.close();
//                }
//            }


