package com.system.sm.service.impl;


import com.system.sm.dao.DepartmentDao;
import com.system.sm.entity.Department;
import com.system.sm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层的实现
 *
 */
//业务层的一个主键
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    /**
     *关联departmentdao
     */
    @Autowired
    private DepartmentDao departmentDao;

    public void add(Department department) {
        departmentDao.insert(department);
    }

    public void remove(Integer id) {
        departmentDao.delete(id);

    }

    public void edit(Department department) {
        departmentDao.update(department);

    }

    public Department get(Integer id) {
        return departmentDao.selectById(id);
    }

    public List<Department> getALL() {
        return departmentDao.selectAll();
    }
}
