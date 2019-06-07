package com.system.sm.dao;



import com.system.sm.entity.Staff;
import org.springframework.stereotype.Repository;

import java.util.List;

//持久化接口
//dao也要受serve管理
//代表持久层化主键
@Repository("staffDao")
public interface StaffDao {
    void insert(Staff staff);

    void delete(Integer id);

    void update(Staff staff);

    Staff selectById(Integer id);

    List<Staff> selectAll();
}
