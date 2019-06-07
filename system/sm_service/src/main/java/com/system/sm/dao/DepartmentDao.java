package com.system.sm.dao;


import com.system.sm.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

//持久化接口
//dao也要受serve管理
//代表持久层化主键
@Repository("departmentDao")
public interface DepartmentDao {
    void insert(Department department);

    void delete(Integer id);

    void update(Department department);

    Department selectById(Integer id);

    List<Department> selectAll();
}
