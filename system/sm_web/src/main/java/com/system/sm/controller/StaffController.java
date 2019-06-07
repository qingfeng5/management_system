package com.system.sm.controller;



import com.system.sm.entity.Department;
import com.system.sm.entity.Staff;

import com.system.sm.service.DepartmentService;
import com.system.sm.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 实现控制器
 */
@Controller("staffController")
public class StaffController {

    //访问业务层对象，把它注入进来
    @Autowired
    private StaffService staffService;
   @Autowired
   private DepartmentService departmentService;

    //增删改查第一个功能
    //department/list.do      /department_list.jsp
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //访问业务处理业务处理对象的功能，把所有的部门拿过来，在jsp页面做展示

        //获取所有部门信息
        List<Staff> list =staffService.getALL();
        //把部门信息放置requset里面
        request.setAttribute("LIST",list);
        //最后转发页面，不能重定向，重定向时传不过去的
        //实现控制器
        request.getRequestDispatcher("../staff_list.jsp").forward(request,response);

    }

    /**
     * 添加界面，访问控制器
     * 添加界面中不需要任何数据
     * 直接转发到jsp页面上
     */
    public void toAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        List<Department> list =departmentService.getALL();
        request.setAttribute("DLIST",list);
        request.getRequestDispatcher("../staff_add.jsp").forward(request,response);
    }


    /**
     *add.do就是另外一种控制器
     * 需要add的功能
     */
    public void add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
       String account = request.getParameter("account");
       String name = request.getParameter("name");
       String sex = request.getParameter("sex");
       String idNumber = request.getParameter("idNumber");
       String info = request.getParameter("info");
       Date bornDate =null;
       //这里是string转date的类型，用的构造SimpleDateFormat转换器
        //指定这个指定的格式，最后用parse把一个字符转化成date类型
        try {
            bornDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("bornDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //接受部门编号，也需要一个转换
        Integer did = Integer.parseInt(request.getParameter("did"));

        //添加这些值
        Staff staff =new Staff();
        staff.setInfo(info);
        staff.setBornDate(bornDate);
        staff.setIdNumber(idNumber);
        staff.setDid(did);
        staff.setAccount(account);
        staff.setName(name);
        staff.setSex(sex);

        //进行添加，跳转回list.do列表界面
        staffService.add(staff);
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
        Staff staff = staffService.get(id);
        //把department放到request
        request.setAttribute("OBJ",staff);

        List<Department> list = departmentService.getALL();
        request.setAttribute("DLIST",list);
        //转发到修改界面
        request.getRequestDispatcher("../staff_edit.jsp").forward(request,response);
    }

    /**
     *页面接到这些值，填充到它的文本框中去，最好点击提交就会提交这个edit
     */
    public void edit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String account = request.getParameter("account");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String idNumber = request.getParameter("idNumber");
        String info =request.getParameter("info");
        Date bornDate=null;
        try {
            bornDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("bornDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer did = Integer.parseInt(request.getParameter("did"));

        /**
         *   把所有信息接收到以后，构造staff对象的时候
         *   从数据库里面，staffService业务层就是数据库拿出来
         *   根据员工编号，把员工信息拿出来，就是staff都是有值
         *   没有提交过来的数据库里面的数据还是数据库里面已有的值
         *   这样数据库更新，不会把已有的数据去掉
         */

        Staff staff = staffService.get(id);
        staff.setInfo(info);
        staff.setBornDate(bornDate);
        staff.setIdNumber(idNumber);
        staff.setDid(did);
        staff.setAccount(account);
        staff.setName(name);
        staff.setSex(sex);

        staffService.edit(staff);
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
        staffService.remove(id);
        response.sendRedirect("list.do");
    }

    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Staff staff = staffService.get(id);
        request.setAttribute("OBJ",staff);
        request.getRequestDispatcher("../staff_detail.jsp").forward(request,response);
    }

}
