package com.nogemasa.management.mapper.store;

import com.nogemasa.management.pojo.EmployeePojo;

import java.util.List;

/**
 * <br/>create at 15-8-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface EmployeeMapper {
    List<EmployeePojo> listEmployeeByStoreSid(String storeSid);

    EmployeePojo getEmployeeBySid(String sid);

    void addEmployee(EmployeePojo employee);

    void updateEmployee(EmployeePojo employee);
}
