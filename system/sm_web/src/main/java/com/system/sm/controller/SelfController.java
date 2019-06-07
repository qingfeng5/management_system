package com.system.sm.controller;

import com.system.sm.entity.Staff;
import com.system.sm.service.SelfService;
import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller("selfController")
public class SelfController {

    @Autowired
    private SelfService selfService;

    //   /toLogin.do
    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.getRequestDispatcher("login.jsp").forward(request,response);
    }

    /**
     *   /login.do
     *   中间登录操作
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取账户和密码
        String account =request.getParameter("account");
        String password = request.getParameter("password");

        //获取完后调用业务层的login方法，来获取当前登录的员工
        //需要业务处理对象，前面注入进来,调用这个方法
        //如果验证成功，返回一个是可用的对象，如果失败的话，返回一个空
        Staff staff = selfService.login(account,password);
        if (staff==null){
            //登录失败，重定向登录去
            response.sendRedirect("toLogin.do");
        }else {
            //否则登录成功，需要把这个staff放在session中
            HttpSession session =request.getSession();
            session.setAttribute("USER",staff);
            //然后重定向到主界面
            response.sendRedirect("main.do");
        }
    }


    /**
     *   /logout.do
     *     退出操作
     */

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //把session这个里面的用户清掉，然后跳转到登录界面tologin
        HttpSession session =request.getSession();
            //清掉就把USER这个值为空
        session.setAttribute("USER", null);
            //然后重定向到主界面
        response.sendRedirect("toLogin.do");

    }


    /**
     *  /main.do
     *  打开主界面，后台管理的框架页
     */
    public void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    /**
     *  self/info.do
     *  查看个人信息和修改个人密码
     */
    public void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../info.jsp").forward(request,response);
    }

    /**
     *  self/toChangePassword.do
     * 修改个人密码
     * 两个流程，打开修改密码界面和修改密码处理
     */
    public void toChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../change_password.jsp").forward(request,response);
    }

    //修改密码处理
    //   self/ChangePassword.do
    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password =request.getParameter("password");
        String password1 =request.getParameter("password1");
        HttpSession session=request.getSession();
        //从staff中拿出staff拿出来
        Staff staff= (Staff) session.getAttribute("USER");
        //如果原始密码与新密码不一置，代表原始密码输错了，就去toChangePassword
        if (!staff.getPassword().equals(password)){
            response.sendRedirect("toChangePassword.do");
        }else {
            //原始密码正确
            //调用新密码，传入新密码
            selfService.changePassword(staff.getId(),password1);
            //重定向到登录界面，直接跳转到登录界面不合理，还会有用户的信息，跳转到退出的控制器../logout.do
            //response.sendRedirect("../logout.do");
            //访问toChangePassword是从右下角的窗口提交过来的，经过相应处理会显示对应的界面
            //在右下角显示登录界面，显然很不顺眼，故希望整个窗体去转向
            response.getWriter().print("<script type=\"text/javascript\">parent.location.href=\"../logout.do\"</script>");
        }
    }
}
