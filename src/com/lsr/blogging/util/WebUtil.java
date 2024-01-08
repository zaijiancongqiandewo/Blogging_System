package com.lsr.blogging.util;


import com.alibaba.fastjson.JSON;
import com.lsr.blogging.common.Result;
import org.apache.catalina.webresources.Cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class WebUtil {

    public static <T> T readJson(HttpServletRequest request, Class<T> clazz)  {
        T t =null;
        BufferedReader reader = null;
        try{
            reader = request.getReader();
            StringBuffer buffer =new StringBuffer();
            String line =null;
            while((line = reader.readLine())!= null){
                buffer.append(line);
            }
            t= JSON.parseObject(buffer.toString(),clazz);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return t;
    }
    // 将Result对象转换成JSON串并放入响应对象
    public static void writeJson(HttpServletResponse response, Result result){
        response.setContentType("application/json;charset=UTF-8");
        try {
            String json =JSON.toJSONString(result);
            response.getWriter().write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
