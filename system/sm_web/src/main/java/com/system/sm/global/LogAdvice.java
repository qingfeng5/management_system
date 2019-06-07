package com.system.sm.global;

import com.system.sm.entity.Log;
import com.system.sm.entity.Staff;
import com.system.sm.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 实现切面话处理，减少开发压力
 */

@Component
@Aspect
public class LogAdvice {
    @Autowired
    private LogService logService;

    //操作日志
    //实现增强功能
    //把controller下面所有方法增强，就是植入
    //屏蔽selfcontroller，因为这是登录相关的，不需要日志处理
    //toadd跟add只需要add方法，那么就要屏蔽所有to的连接词的方法
    @After("execution(* com.system.sm.controller.*.*(..)) && !execution(* com.system.sm.controller.SelfController.*(..))" +
            "&& !execution(* com.system.sm.controller.*.to*(..))")
    public void operationLog(JoinPoint joinPoint){
        Log log=new Log();
        //控制器的类名设置下，这是当前的要增强的目标对象，有了对象后获得类，通过类获得类名
        log.setModule(joinPoint.getTarget().getClass().getSimpleName());
        //当前操作
        log.setOperation(joinPoint.getSignature().getName());
        //操作员拿到request就能一步步拿出来
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session =request.getSession();
        Object obj = session.getAttribute("USER");
        Staff staff = (Staff) obj;
        log.setOperator(staff.getAccount());
        //最后不出现异常就成功
        log.setResult("成功");
        logService.addOperationLog(log);
        //接来就是植入，植入时机
    }

    /**
     * 系统日志
     */
    //出现异常后处理
    @AfterThrowing(throwing = "e",pointcut = "execution(* com.system.sm.controller.*.*(..)) && !execution(* com.system.sm.controller.SelfController.*(..))")
    public void systemLog(JoinPoint joinPoint,Throwable e){
        Log log=new Log();
        //控制器的类名设置下，这是当前的要增强的目标对象，有了对象后获得类，通过类获得类名
        log.setModule(joinPoint.getTarget().getClass().getSimpleName());
        //当前操作
        log.setOperation(joinPoint.getSignature().getName());
        //操作员拿到request就能一步步拿出来
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session =request.getSession();
        Object obj = session.getAttribute("USER");
        Staff staff = (Staff) obj;
        log.setOperator(staff.getAccount());
        //最后不出现结果不能为成功，出现异常，异常的结果名字出现出来
        log.setResult(e.getClass().getSimpleName());
        logService.addSystemLog(log);

    }

    /**
     * 登录日志
     */
    //出现异常后处理
    @After("execution(* com.system.sm.controller.SelfController.login(..))")
    public void LoginLog(JoinPoint joinPoint){
       log(joinPoint);
    }

    /**
     * 退出日志
     */
    //退出没有失败的说法
    @Before("execution(* com.system.sm.controller.SelfController.login(..))")
    public void logoutLog(JoinPoint joinPoint){
        log(joinPoint);
    }

    //定义一个私有的方法
    //在这两个方法里面调用就行
    //退出和登录规则是一样的
    private void log(JoinPoint joinPoint){
        Log log=new Log();
        //控制器的类名设置下，这是当前的要增强的目标对象，有了对象后获得类，通过类获得类名
        log.setModule(joinPoint.getTarget().getClass().getSimpleName());
        //当前操作
        log.setOperation(joinPoint.getSignature().getName());
        //操作员拿到request就能一步步拿出来
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session =request.getSession();
        Object obj = session.getAttribute("USER");

        //如果登录失败事不知道的
        if (obj==null){
            //调用登录的这个控制器时候，用户事会提交账户名和密码。拿到账户，提交就行了
            log.setOperator(request.getParameter("account"));
            log.setResult("失败");
        }else {
            Staff staff = (Staff) obj;
            log.setOperator(staff.getAccount());
            log.setResult("成功");
        }
        logService.addLoginLog(log);
    }

}
