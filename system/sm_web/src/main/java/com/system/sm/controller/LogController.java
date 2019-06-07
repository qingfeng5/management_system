package com.system.sm.controller;

import com.system.sm.entity.Department;
import com.system.sm.entity.Log;
import com.system.sm.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("logController")
public class LogController {
    @Autowired
    private LogService logService;

    //三种控制器相似太高，可以直接加个类型，区分

    public void operationLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //访问业务处理业务处理对象的功能，把所有的部门拿过来，在jsp页面做展示
        //获取所有部门信息
        List<Log> list =logService.getOperationLog();
        //把部门信息放置requset里面
        request.setAttribute("LIST",list);
        //最后转发页面，不能重定向，重定向时传不过去的
        //实现控制器
        request.setAttribute("TYPE","操作");
        request.getRequestDispatcher("../log_list.jsp").forward(request,response);
    }

    public void loginLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Log> list =logService.getLoginLog();
        //把部门信息放置requset里面
        request.setAttribute("LIST",list);
        request.setAttribute("TYPE","登录");
        request.getRequestDispatcher("../log_list.jsp").forward(request,response);
    }

    public void systemLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Log> list =logService.getSystemLog();
        //把部门信息放置requset里面
        request.setAttribute("LIST",list);
        request.setAttribute("TYPE","系统");
        request.getRequestDispatcher("../log_list.jsp").forward(request,response);
    }
}
