package com.system.sm.service.impl;

import com.system.sm.dao.SelfDao;
import com.system.sm.dao.StaffDao;
import com.system.sm.entity.Staff;
import com.system.sm.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("selfService")
public class SelfServiceImpl implements SelfService {

    @Autowired
    private SelfDao selfDao;
    @Autowired
    private StaffDao staffDao;

    //登录密码的功能
    public Staff login(String account, String password) {
        Staff staff =selfDao.selectByAccount(account);
        //如果staff为空
        if(staff==null)return null;
        //如果staff不为空
        if(staff.getPassword().equals(password))return staff;
        return null;
    }

    //修改密码的功能
    public void changePassword(Integer id, String password) {
        Staff staff =staffDao.selectById(id);
        //给staff这个password属性设置为新的password，更改
        staff.setPassword(password);
        staffDao.update(staff);
    }
}
