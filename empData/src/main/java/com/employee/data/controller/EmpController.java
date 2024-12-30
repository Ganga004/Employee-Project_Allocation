package com.employee.data.controller;

import com.employee.data.entity.Employee;
import com.employee.data.model.ApiResponse;
import com.employee.data.model.EmpRequest;
import com.employee.data.model.EmpResponse;
import com.employee.data.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addEmployee(@RequestBody EmpRequest empRequest) throws ParseException {
        log.info("EmpController::addEmployee {}", empRequest);
        Employee savedEmployee = empService.addEmployee(empRequest);
        return new ResponseEntity<>(new ApiResponse("Employee Data Added", savedEmployee), HttpStatus.CREATED);
    }

    @GetMapping("/second/most/experience")
    public ResponseEntity<ApiResponse> getSecondMostExperienced() {
        log.info("EmpController::getSecondMostExperience");
        EmpResponse empResponse = empService.getEmployee();
        return new ResponseEntity<>(new ApiResponse("SECOND MOST EXPERIENCED EMPLOYEE", empResponse), HttpStatus.OK);
    }

    @GetMapping("/skills")
    public ResponseEntity<ApiResponse> getEmployeeDataBasedOnPrimaryAndSecondarySkills(@RequestParam String primarySkills,
                                                                                       @RequestParam String secondarySkills) {
        log.info("EmpController::getEmployeeDataBasedOnPrimaryAndSecondarySkills {} {}", primarySkills, secondarySkills);
        List<Employee> empList = empService.getListOfEmployeesBasedOnPrimaryAndSecondarySkills(primarySkills, secondarySkills);
        return new ResponseEntity<>(new ApiResponse("Employee Details Based On Primary & Secondary Skills",
                empList), HttpStatus.OK);
    }

    @GetMapping("/not/allocated/skills")
    public ResponseEntity<ApiResponse> listOfNotAllocatedSkillsEmployeeDetails(@RequestParam String skills) {
        log.info("EmpController::listOfNotAllocatedSkillsEmployeeDetails {}", skills);
        List<Employee> empList = empService.listOfNotAllocatedEmpDetails(skills);
        return new ResponseEntity<>(new ApiResponse("Primary Skills not Allocated for these Employees", empList), HttpStatus.OK);
    }
}
