package com.system.sm.controller;


import com.system.sm.entity.Department;
import com.system.sm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 实现控制器
 */
@Controller("departmentController")
public class DepartmentController {

    //访问业务层对象，把它注入进来
    @Autowired
    private DepartmentService departmentService;

    //增删改查第一个功能
    //department/list.do      /department_list.jsp
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //访问业务处理业务处理对象的功能，把所有的部门拿过来，在jsp页面做展示

        //获取所有部门信息
        List<Department> list =departmentService.getALL();
        //把部门信息放置requset里面
        request.setAttribute("LIST",list);
        //最后转发页面，不能重定向，重定向时传不过去的
        //实现控制器
        request.getRequestDispatcher("../department_list.jsp").forward(request,response);

    }

    /**
     * 添加界面，访问控制器
     * 添加界面中不需要任何数据
     * 直接转发到jsp页面上
     */
    public void toAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../department_add.jsp").forward(request,response);
    }


    /**
     *add.do就是另外一种控制器
     * 需要add的功能
     */
    public void add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //首先接值，name文本框的值
       String name =request.getParameter("name");
       String address =request.getParameter("address");

       //构造department对象，把接到的值出传给属性里面去
        Department department =new Department();
        department.setName(name);
        department.setAddress(address);

        //然后调用departmentService业务处理类的添加功能，把对象添加进去，保存下来
        departmentService.add(department);
        //回控制器，不需要用request传值，建议使用使用重定向
        response.sendRedirect("list.do");
    }

    /**
     * 修改这个界面
     * 分两步，打开修改界面，提交修改操作
     */

    public void toEdit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //要修改，首先告诉我，修改哪一个那个对象，编号多少，id形式告知
        //访问控制器，首先传递一个参数
        Integer id = Integer.parseInt(request.getParameter("id"));
        //接到id后，我访问业务处理对象，然后调用它的get方法。获取想要get发方法，然后把这个方法
        Department department = departmentService.get(id);
        //把department放到request
        request.setAttribute("OBJ",department);
        //转发到修改界面
        request.getRequestDispatcher("../department_edit.jsp").forward(request,response);
    }

    /**
     *页面接到这些值，填充到它的文本框中去，最好点击提交就会提交这个edit
     */
    public void edit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //提交时必须把这个id也提交过来，修改也需要id
        Integer id = Integer.parseInt(request.getParameter("id"));
        //首先接值，name文本框的值
        String name =request.getParameter("name");
        String address =request.getParameter("address");

        //构造department对象，把接到的值出传给属性里面去
        Department department =new Department();
        //那么向业务层id传递，这个id是有值的
        department.setId(id);
        department.setName(name);
        department.setAddress(address);

        //然后调用departmentService业务处理类的添加功能，把对象添加进去，保存下来
        departmentService.edit(department);
        //回控制器，不需要用request传值，建议使用使用重定向
        response.sendRedirect("list.do");
    }

    /**
     *删除
     */

    public void remove(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //要修改，首先告诉我，修改哪一个那个对象，编号多少，id形式告知
        //访问控制器，首先传递一个参数
        Integer id = Integer.parseInt(request.getParameter("id"));
        //接到id后，我访问业务处理对象，然后调用它的get方法。获取想要get发方法，然后把这个方法
         departmentService.remove(id);
        response.sendRedirect("list.do");
    }

}
