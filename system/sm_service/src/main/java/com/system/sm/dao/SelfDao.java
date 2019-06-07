package com.system.sm.dao;

import com.system.sm.entity.Staff;
import org.springframework.stereotype.Repository;

/**
 * 设置登录界面
 */
//在ioc容器中把他注册下
@Repository("selfDao")
public interface SelfDao {
    //首先登录相关的方法，传递过来一个账户名
    //给我一个账户名，我把数据库里面的跟账户名匹配的员工对象拿过来
    //之后，根据password值跟输入的密码进行对比，如果一样代表登录成功，否则就失败，不要拼接
    Staff selectByAccount(String account);

    /**
     * 对持久化操作还涉及其他功能
     * 修改密码等等操作，但这些操作可以调用StaffDao已有的功能
     */

}
