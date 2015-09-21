package com.nogemasa.management.service.store.impl;

import com.nogemasa.management.mapper.store.EmployeeMapper;
import com.nogemasa.management.pojo.EmployeePojo;
import com.nogemasa.management.service.store.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <br/>create at 15-8-13
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("employeeService")
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeePojo> listEmployeeByStoreSid(String storeSid) {
        return employeeMapper.listEmployeeByStoreSid(storeSid);
    }

    @Override
    public void addEmployee(EmployeePojo employee) {
        if (employee == null || employee.getName() == null || employee.getName().trim().isEmpty()) {
            return;
        }
        if (employee.getEntryTime() == null) {
            employee.setEntryTime(new Date());
        }
        employeeMapper.addEmployee(employee);
    }

    @Override
    public void updateEmployee(EmployeePojo employee) {
        if (employee == null || employee.getSid() == null || employee.getSid().trim().isEmpty()
                || employee.getName() == null || employee.getName().trim().isEmpty()) {
            return;
        }
        employeeMapper.updateEmployee(employee);
    }
}
