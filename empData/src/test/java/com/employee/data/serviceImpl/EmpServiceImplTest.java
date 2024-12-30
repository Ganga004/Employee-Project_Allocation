package com.employee.data.serviceImpl;

import com.employee.data.ExternalClient.ProjectClient;
import com.employee.data.entity.CapabilityCentre;
import com.employee.data.entity.Designation;
import com.employee.data.entity.Employee;
import com.employee.data.exception.EmployeeCustomException;
import com.employee.data.model.EmpRequest;
import com.employee.data.model.ProjectResponse;
import com.employee.data.repository.EmpRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmpServiceImplTest {
    @Mock
    private EmpRepository empRepository;

    @Mock
    private ProjectClient projectClient;

    @InjectMocks
    private EmpServiceImpl empServiceImpl;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addEmployeeTest() throws ParseException {
        EmpRequest request = EmpRequest.builder().employeeName("Raja").designation(Designation.ARCHITECT)
                .capabilityCentre(CapabilityCentre.DEP_CLOUD).primarySkill("Java,.Net,Cloud")
                .secondarySkill("Python,Angular").dateOfJoining(LocalDate.now()).overallExperience(11.3D).build();
        empServiceImpl.addEmployee(request);
    }

    @Test
    public void addEmployeeWithInvalidNameTest() throws ParseException {
        EmpRequest request = EmpRequest.builder().employeeName("").build();
        Assertions.assertThrows((EmployeeCustomException.class), () -> empServiceImpl.addEmployee(request));
    }

    @Test
    public void listOfNotAllocatedEmpDetailsWithOneSKillsTest() {
        empServiceImpl.listOfNotAllocatedEmpDetails("Java");
    }

    @Test
    public void listOfNotAllocatedEmpDetailsWithTwoSKillsTest() {
        empServiceImpl.listOfNotAllocatedEmpDetails("Java,Ruby");
    }

    @Test
    public void listOfNotAllocatedEmpDetailsWithThreeSKillsTest() {
        empServiceImpl.listOfNotAllocatedEmpDetails("Java,Ruby,Angular");
    }

    @Test
    public void listOfNotAllocatedEmpDetailsWithFourSKillsTest() {
        empServiceImpl.listOfNotAllocatedEmpDetails("Java,Ruby,Angular,NodeJs");
    }

    @Test
    public void getEmployeeTest() {
        Employee employee= Employee.builder().employeeId(2L).employeeName("Raja")
                .capabilityCentre(CapabilityCentre.DEP_CLOUD).designation(Designation.ARCHITECT)
                .dateOfJoining(LocalDate.now()).overallExperience(12.7).primarySkill("Java,Ruby,Pythong")
                .secondarySkill("Angular,Javascript").build();
        ProjectResponse response = ProjectResponse.builder().build();
        List<ProjectResponse> projectLIst = new LinkedList<>();
        projectLIst.add(response);
        Mockito.when(empRepository.findSecondMostExperiencedEmployee()).thenReturn(employee);

        Mockito.when(projectClient.getProjectDetails(any())).thenReturn(ResponseEntity.ok(projectLIst));
        empServiceImpl.getEmployee();
    }

    @Test
    public void getEmployeeTestWithException() {
        Employee employee= Employee.builder().employeeId(2L).employeeName("Raja")
                .capabilityCentre(CapabilityCentre.DEP_CLOUD).designation(Designation.ARCHITECT)
                .dateOfJoining(LocalDate.now()).overallExperience(12.7).primarySkill("Java,Ruby,Pythong")
                .secondarySkill("Angular,Javascript").build();
        Mockito.when(empRepository.findSecondMostExperiencedEmployee()).thenReturn(employee);
        Mockito.when(projectClient.getProjectDetails(any())).thenThrow(new RuntimeException("Project service is unavailable"));
        Exception exception = assertThrows(EmployeeCustomException.class, () -> {
            empServiceImpl.getEmployee();
        });
        assertEquals("Project Service is unavailable", exception.getMessage());
    }

    @Test
    public void getListOfEmployeesBasedOnPrimaryAndSecondarySkillsTest() {
        String primarySkill = "Java,Angular";
        String secondarySkill = "HTML,CSS";
        Employee employee= Employee.builder().employeeId(2L).employeeName("Raja")
                .capabilityCentre(CapabilityCentre.DEP_CLOUD).designation(Designation.ARCHITECT)
                .dateOfJoining(LocalDate.now()).overallExperience(12.7).primarySkill("Java,Angular,Ruby,Pythong")
                .secondarySkill("HTML,CSS,Angular,Javascript").build();
        List<Employee> listEmployee = new LinkedList<>();
        listEmployee.add(employee);

        Mockito.when(empRepository.findEmployeesListBasedOnPrimaryAndSecondarySkills(anyString(), anyString()
                , anyString(), anyString())).thenReturn(listEmployee);
        empServiceImpl.getListOfEmployeesBasedOnPrimaryAndSecondarySkills(primarySkill, secondarySkill);

    }

}


