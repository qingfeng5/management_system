package com.system.sm.service.impl;

import com.system.sm.dao.StaffDao;
import com.system.sm.entity.Staff;
import com.system.sm.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("staffService")
public class StaffServiceImpl implements StaffService {

    //调用持久功能
    @Autowired
    private StaffDao staffDao;


    public void add(Staff staff) {
        //基本业务规则
        staff.setPassword("123456");
        staff.setWorkTime(new Date());
        staff.setStatus("正常");
        //编辑好了，调用编辑staffdao插入进去
        staffDao.insert(staff);
    }

    public void remove(Integer id) {
        staffDao.delete(id);
    }

    public void edit(Staff staff) {
        staffDao.update(staff);
    }

    public Staff get(Integer id) {
        return staffDao.selectById(id);
    }

    public List<Staff> getALL() {
        return staffDao.selectAll();
    }
}
