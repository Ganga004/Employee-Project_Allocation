package com.employee.data.controller;

import com.employee.data.entity.CapabilityCentre;
import com.employee.data.entity.Designation;
import com.employee.data.entity.Employee;
import com.employee.data.model.EmpRequest;
import com.employee.data.model.EmpResponse;
import com.employee.data.service.EmpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class EmpControllerTest {
    @Mock
    private EmpService empService;

    @InjectMocks
    private EmpController empController;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(empController).build();
    }

    @Test
    public void addEmployeeTest() throws Exception {
        EmpRequest request = EmpRequest.builder().employeeName("Raja").overallExperience(3.5D)
                .capabilityCentre(CapabilityCentre.DEP_CLOUD).designation(Designation.SENIOR_ENGINEER)
                .primarySkill("Java,Python").secondarySkill("Angular").build();

        Employee employee = Employee.builder().employeeId(2L).employeeName("Raja").overallExperience(3.5D)
                .capabilityCentre(CapabilityCentre.DEP_CLOUD).designation(Designation.SENIOR_ENGINEER)
                .primarySkill("Java,Python").secondarySkill("Angular").build();
        when(empService.addEmployee(any(EmpRequest.class))).thenReturn(employee);

        MvcResult result = mockMvc.perform(post("/emp/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employee Data Added"))
                .andExpect(jsonPath("$.data.employeeId").value(2))
                .andExpect(jsonPath("$.data.employeeName").value("Raja"))
                .andExpect(jsonPath("$.data.primarySkill").value("Java,Python"))
                .andExpect(jsonPath("$.data.secondarySkill").value("Angular"))
                .andReturn();

        verify(empService, times(1)).addEmployee(any(EmpRequest.class));
    }


    @Test
    public void GetSecondMostExperiencedEmpTest() throws Exception {
        EmpResponse response = EmpResponse.builder().employeeId(2L).employeeName("Raja").overallExperience(3.5D)
                .capabilityCentre(CapabilityCentre.DEP_CLOUD).designation(Designation.SENIOR_ENGINEER)
                .primarySkill("Java,Python").secondarySkill("Angular").build();
        when(empService.getEmployee()).thenReturn(response);

        MvcResult result = mockMvc.perform(get("/emp/second/most/experience"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SECOND MOST EXPERIENCED EMPLOYEE"))
                .andExpect(jsonPath("$.data.employeeId").value(2))
                .andExpect(jsonPath("$.data.employeeName").value("Raja"))
                .andExpect(jsonPath("$.data.capabilityCentre").value("DEP_CLOUD"))
                .andExpect(jsonPath("$.data.designation").value("SENIOR_ENGINEER"))
                .andExpect(jsonPath("$.data.primarySkill").value("Java,Python"))
                .andExpect(jsonPath("$.data.secondarySkill").value("Angular"))
                .andExpect(jsonPath("$.data.overallExperience").value(3.5))
                .andReturn();

        // Verify that the service method was called once
        verify(empService, times(1)).getEmployee();
    }

    @Test
    public void getEmployeeDataBasedOnPrimaryAndSecondarySkillsTest() throws Exception {
        Employee employee = Employee.builder().employeeId(2L).employeeName("Raja").overallExperience(3.5D)
                .capabilityCentre(CapabilityCentre.DEP_CLOUD).designation(Designation.SENIOR_ENGINEER)
                .primarySkill("Java,Python").secondarySkill("Angular").build();
        List<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);
        when(empService.getListOfEmployeesBasedOnPrimaryAndSecondarySkills("Java", "Angular"))
                .thenReturn(employeeList);
        MvcResult result = mockMvc.perform(get("/emp/skills")
                        .param("primarySkills", "Java")
                        .param("secondarySkills", "Angular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee Details Based On Primary & Secondary Skills"))
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data[0].employeeId").value(2))
                .andExpect(jsonPath("$.data[0].employeeName").value("Raja"))
                .andExpect(jsonPath("$.data[0].primarySkill").value("Java,Python"))
                .andExpect(jsonPath("$.data[0].secondarySkill").value("Angular"))
                .andExpect(jsonPath("$.data[0].overallExperience").value(3.5))
                .andReturn();
        verify(empService, times(1)).getListOfEmployeesBasedOnPrimaryAndSecondarySkills("Java", "Angular");
    }

}