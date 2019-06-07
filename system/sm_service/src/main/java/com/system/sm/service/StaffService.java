package com.system.sm.service;

import com.system.sm.entity.Department;
import com.system.sm.entity.Staff;

import java.util.List;

public interface StaffService {

    void add(Staff staff);
    void remove(Integer id);
    void edit(Staff staff);
    Staff get(Integer id);
    List<Staff> getALL();
}
