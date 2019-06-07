package com.system.sm.service;

import com.system.sm.entity.Log;

import java.util.List;

public interface LogService {
    /**
     * 分成3种类型，不希望持久层知道，不需要表现层知道
     * 把这些方法分开
     * 关于日志的处理就是查看与添加
     */

    //记录日志
    void addSystemLog(Log log);
    void addLoginLog(Log log);
    void addOperationLog(Log log);

    List<Log> getSystemLog();
    List<Log> getLoginLog();
    List<Log> getOperationLog();
}
