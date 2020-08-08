package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

//@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        /* 完成方法分发*/

        // 1 获取请求路径
        String uri = req.getRequestURI();
//        System.out.println(uri);
        // 2 获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
//        System.out.println(methodName);

        // 3 获取方法对象Method
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 4 执行方法
            method.invoke(this, req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接将传入的对象序列化为json
     */
    public void writeValue(Object obj, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        try {
            mapper.writeValue(response.getOutputStream(), obj);
//            System.out.println(mapper.writeValueAsString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回字符串的json
     */
    public String writeValueAsString(Object obj) {
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
             json = mapper.writeValueAsString(obj);
             return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
