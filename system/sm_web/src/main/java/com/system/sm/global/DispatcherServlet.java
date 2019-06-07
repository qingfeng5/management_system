package com.system.sm.global;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 配置核心控制器
 */
public class DispatcherServlet extends GenericServlet {

    //初始化ioc容器，每次请求就不会加载配置文件了
    private ApplicationContext context;

    public void init() throws ServletException {
        super.init();
        context =new ClassPathXmlApplicationContext("spring.xml");
    }
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //强转ServletRequest，ServletResponse
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /**
         * /staff/add.do      /login.do
         * 以控制器来说
         * staffController
         *  public void add(HttpServletRequest request, HttpServletResponse response){}
         */

        //首先获取用户请求的url，获取请求路径
        //解析出来只要/staff/add,不需要add根，删掉一个根号
        String path =request.getServletPath().substring(1);
        //希望url获取两个名字，一个时ioc容器bean名字，还有一个是处理这次请求的方法名
        String beanName =null;
        String methodName =null;

        //登录时有分隔符，对其进行判断
        int index =path.indexOf('/');
        if (index !=-1){
            ///staff/add.do从开头开始截，一直截到第一个斜杠为止
            beanName =path.substring(0,index)+"Controller";
            //staff/add.do后面位置开始截取，一直截取到.do这里
            methodName =path.substring(index + 1, path.indexOf(".do"));
        }else {
            beanName ="selfController";
            // /staff/add.do      /login.do一直截取到后面的。do
            methodName = path.substring(0, path.indexOf(".do"));
        }

        //加载ioc容器，每次请求都要配置，容量太大了，把这个提出去
        // ApplicationContext context =new ClassPathXmlApplicationContext("springservice.xml");
        Object obj =context.getBean(beanName);
        try {
            Method method =obj.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            //下来调用invoke方法执行方法
            method.invoke(obj,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }


}
