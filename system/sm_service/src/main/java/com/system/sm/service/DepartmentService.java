package com.system.sm.service;


import com.system.sm.entity.Department;

import java.util.List;

/**
 * 业务层接口
 */
public interface DepartmentService {
    /**
     * @param department
     * 如果一个特别简单的，没有任何特殊业务功能
     * 数据增删改查，业务层方法通常跟持久层方法五个方法，一一对应
     * 但是业务层有自己功能的，绝对不是一一对应
     */
    //添加删除信息
    void add(Department department);
    void remove(Integer id);
    void edit(Department department);
    Department get(Integer id);
    List<Department> getALL();
}
