package com.employee.data.serviceImpl;

import com.employee.data.ExternalClient.ProjectClient;
import com.employee.data.entity.CapabilityCentre;
import com.employee.data.entity.Designation;
import com.employee.data.entity.Employee;
import com.employee.data.exception.EmployeeCustomException;
import com.employee.data.model.EmpRequest;
import com.employee.data.model.EmpResponse;
import com.employee.data.model.ProjectResponse;
import com.employee.data.repository.EmpRepository;
import com.employee.data.service.EmpService;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private ProjectClient projectClient;

    @Override
    public Employee addEmployee(EmpRequest empRequest) throws ParseException {
        log.info("EmpServiceImpl::EmpServiceImpl");
        Employee employee = Employee.builder().employeeName(validateAndGetEmpName(empRequest.getEmployeeName()))
                .capabilityCentre(validateAndGetCapabilityCentre(String.valueOf(empRequest.getCapabilityCentre())))
                .dateOfJoining(validateAndGetDateOfJoining(empRequest.getDateOfJoining()))
                .designation(validateAndGetDesignation(String.valueOf(empRequest.getDesignation())))
                .primarySkill(validateAndGetSkills(empRequest.getPrimarySkill()))
                .secondarySkill(validateAndGetSkills(empRequest.getSecondarySkill()))
                .overallExperience(validateAndGetOverAllYearsOfExperience(empRequest.getOverallExperience()))
                .build();
        empRepository.save(employee);
        return employee;
    }

    @Override
    public List<Employee> listOfNotAllocatedEmpDetails(String primarySkills) {
        log.info("EmpServiceImpl::listOfNotAllocatedEmpDetails");
        return getListOfEmployeesBasedONSkills(primarySkills);
    }

    private List<Employee> getListOfEmployeesBasedONSkills(String primarySkills) {
        log.info("EmpServiceImpl::getListOfEmployeesBasedONSkills");
        String[] skills = primarySkills.split(",");
        skills = Arrays.stream(skills).map(String::trim).toArray(String[]::new);
        return switch (skills.length) {
            case 1 -> empRepository.findEmployeesByNotAllocatedSkills(skills[0], null, null, null);
            case 2 -> empRepository.findEmployeesByNotAllocatedSkills(skills[0], skills[1], null, null);
            case 3 -> empRepository.findEmployeesByNotAllocatedSkills(skills[0], skills[1], skills[2], null);
            case 4 -> empRepository.findEmployeesByNotAllocatedSkills(skills[0], skills[1], skills[2], skills[3]);
            default -> empRepository.findEmployeesByNotAllocatedSkills(null, null, null, null);
        };
    }

    @Override
    public EmpResponse getEmployee() {
        log.info("EmpServiceImpl::getEmployee");
        Employee employee = empRepository.findSecondMostExperiencedEmployee();
        List<ProjectResponse> listOfProjectResponse = List.of();
        if (employee != null) {
            try {
                listOfProjectResponse = projectClient.getProjectDetails(employee.getEmployeeId()).getBody();
                log.info("Microservice communication.");
            } catch (Exception e) {
                throw new EmployeeCustomException("Project Service is unavailable", "INTERNAL_SERVER_ERROR");
            }
        }
        EmpResponse empResponse = EmpResponse.builder().employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .capabilityCentre(employee.getCapabilityCentre())
                .dateOfJoining(employee.getDateOfJoining())
                .designation(employee.getDesignation())
                .primarySkill(employee.getPrimarySkill())
                .secondarySkill(employee.getSecondarySkill())
                .overallExperience(employee.getOverallExperience())
                .projectDetailsResponse(!listOfProjectResponse.isEmpty() ? listOfProjectResponse : List.of())
                .build();
    
        return empResponse;
    }

    @Override
    public List<Employee> getListOfEmployeesBasedOnPrimaryAndSecondarySkills(String primarySkills, String secondarySkills) {
        log.info("EmpServiceImpl::getListOfEmployeesBasedOnPrimaryAndSecondarySkills");
        String[] primarySkillArray = primarySkills != null ? primarySkills.split(",") : new String[0];
        primarySkillArray = Arrays.stream(primarySkillArray).map(String::trim).toArray(String[]::new);

        String primarySkill1 = primarySkillArray.length > 0 ? primarySkillArray[0] : null;
        String primarySkill2 = primarySkillArray.length > 1 ? primarySkillArray[1] : null;

        String[] secondarySkillArray = secondarySkills != null ? secondarySkills.split(",") : new String[0];
        secondarySkillArray = Arrays.stream(secondarySkillArray).map(String::trim).toArray(String[]::new);

        String secondarySkill1 = secondarySkillArray.length > 0 ? secondarySkillArray[0] : null;
        String secondarySkill2 = secondarySkillArray.length > 1 ? secondarySkillArray[1] : null;
        log.info("Skills value are : {} {} {} {}",primarySkill1,primarySkill2,secondarySkill1,secondarySkill2);
        List<Employee> listOfEmployee = empRepository.findEmployeesListBasedOnPrimaryAndSecondarySkills(primarySkill1, primarySkill2,
                secondarySkill1, secondarySkill2);
        return listOfEmployee.isEmpty() ? List.of() : listOfEmployee;
    }

    @Override
    public List<Employee> getAllEmployeeData() {
        List<Employee> empList = empRepository.findAll();
        return empList;
    }
    
    private static Double validateAndGetOverAllYearsOfExperience(Double yearsOfExperience) {
        if (yearsOfExperience == null || yearsOfExperience.isNaN()) {
            throw new EmployeeCustomException("Enter proper years of experience", "INVALID_YEARS_OF_EXPERIENCE");
        } else {
            return yearsOfExperience;
        }
    }

    private static String validateAndGetSkills(String skills) {
        if (skills == null || skills.isEmpty()) {
            throw new EmployeeCustomException("Enter Proper Primary Skills", "INVALID_PRIMARY_SKILL");
        } else {
            return skills.toUpperCase();
        }
    }

    private static Designation validateAndGetDesignation(String designation) {
        if (designation == null || designation.trim().isBlank()) {
            throw new EmployeeCustomException("Enter proper Designation", "INVALID_DESIGNATION");
        } else {
            Designation result = null;
            for (Designation role : Designation.values()) {
                String normalizedValue = designation.trim().replace(" ", "_").toUpperCase();
                if (role.name().equalsIgnoreCase(normalizedValue)) {
                    result = role;
                    break;
                }
            }
            if (result == null) {
                throw new EmployeeCustomException("Enter Proper Designation", "INVALID_DESIGNATION");
            }
            return result;
        }
    }

    private static LocalDate validateAndGetDateOfJoining(LocalDate dateOfJoining) throws ParseException {
        if (dateOfJoining == null) {
            throw new EmployeeCustomException("Enter Proper Date", "INVALID_DATE");
        } else {
            return dateOfJoining;
        }
    }

    @JsonCreator
    private static CapabilityCentre validateAndGetCapabilityCentre(String capabilityCentre) {

        if (capabilityCentre == null || capabilityCentre.trim().isBlank()) {
            throw new EmployeeCustomException("Enter proper capabilityCenter", "INVALID_CAPABILITY_CENTER");
        } else {
            String normalizedValue = capabilityCentre.trim().replace(" ", "_").toUpperCase();
            CapabilityCentre result = null;
            for (CapabilityCentre role : CapabilityCentre.values()) {
                if (role.name().equalsIgnoreCase(normalizedValue)) {
                    result = role;
                    break;
                }
            }
            if (result == null) {
                throw new EmployeeCustomException("Enter proper capabilityCenter", "INVALID_CAPABILITY_CENTER");
            }
            return result;
        }
    }

    private static String validateAndGetEmpName(String empName) {
        if (empName == null || empName.trim().isBlank()) {
            throw new EmployeeCustomException("Enter Proper Name", "INVALID_NAME");
        } else {
            return empName;
        }
    }
}
