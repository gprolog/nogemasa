package com.nogemasa.management.service.store;

import com.nogemasa.management.pojo.EmployeePojo;

import java.util.List;

/**
 * <br/>create at 15-8-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IEmployeeService {
    List<EmployeePojo> listEmployeeByStoreSid(String storeSid);

    void addEmployee(EmployeePojo employee);

    void updateEmployee(EmployeePojo employee);
}
