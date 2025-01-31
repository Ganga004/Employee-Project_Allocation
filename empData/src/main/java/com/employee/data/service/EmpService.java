package com.employee.data.service;

import com.employee.data.entity.Employee;
import com.employee.data.model.EmpRequest;
import com.employee.data.model.EmpResponse;

import java.text.ParseException;
import java.util.List;

public interface EmpService {
    Employee addEmployee(EmpRequest employee) throws ParseException;

    List<Employee> listOfNotAllocatedEmpDetails(String empRequest);

    EmpResponse getEmployee();

    List<Employee> getListOfEmployeesBasedOnPrimaryAndSecondarySkills(String primarySkills, String secondarySkills);

    List<Employee> getAllEmployeeData();
}
