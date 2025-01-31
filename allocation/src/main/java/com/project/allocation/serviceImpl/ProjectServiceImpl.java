package com.project.allocation.serviceImpl;

import com.project.allocation.entity.Project;
import com.project.allocation.exception.ProjectCustomException;
import com.project.allocation.model.ProjectRequest;
import com.project.allocation.model.ProjectResponse;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.service.ProjectService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Project addProjectDetails(String email, ProjectRequest projectRequest) throws ParseException {
        log.info("ProjectServiceImpl::addProjectDetails {}", projectRequest);
        Integer count = projectRepository.countOfEmployeeProject(projectRequest.getEmpId());
        if (count < 3) {
            Project project = Project.builder().empId(validateAndGetEmpId(projectRequest.getEmpId()))
                    .projectName(validateAndGetProjectName(projectRequest.getProjectName()))
                    .accountName(validateAndGetAccountName(projectRequest.getAccountName()))
                    .allocation(validateAndGetAllocation(projectRequest.getAllocation()))
                    .projectStartDate(validateAngGetProjectDate(projectRequest.getProjectStartDate()))
                    .projectEndDate(validateAngGetProjectDate(projectRequest.getProjectEndDate()))
                    .remarks(validateAndGetRemarks(projectRequest.getRemarks()))
                    .build();

            projectRepository.save(project);
            log.info("Project allocated");
            if(email !=null || email.isEmpty()) {
                sendEmailToEmployee(email, "We are happy to inform you that project is allocated to you"
                        , "Project Allocated");
            }
            return project;
        } else {
            throw new ProjectCustomException("Employee is already allocated to 3 projects", "INVALID_PROJECT_ALLOCATION");
        }
    }

    @Override
    public Project updateProjectDetails(Long projectId, ProjectRequest request) throws ParseException {
        log.info("ProjectServiceImpl::updatProjectDetails");
        if (ObjectUtils.isEmpty(request)) {
            throw new ProjectCustomException("Project Data is Empty", "INVLALID_PROJECT_DETAILS");
        }
        Project existingData = projectRepository.findById(projectId).
                orElseThrow(() -> new ProjectCustomException("Records not available", "DATA_NOT_EXIST"));

        existingData.setEmpId(request.getEmpId() != null ? validateAndGetEmpId(request.getEmpId()) : existingData.getEmpId());
        existingData.setProjectName(request.getProjectName() != null ? validateAndGetProjectName(request.getProjectName())
                : existingData.getProjectName());
        existingData.setAccountName(request.getAccountName() != null ? validateAndGetAccountName(request.getAccountName())
                : existingData.getAccountName());
        existingData.setAllocation(request.getAllocation() != null ? validateAndGetAllocation(request.getAllocation())
                : existingData.getAllocation());
        existingData.setProjectStartDate(request.getProjectStartDate() != null ? validateAngGetProjectDate(request.getProjectStartDate())
                : existingData.getProjectStartDate());
        existingData.setProjectEndDate(request.getProjectEndDate() != null ? validateAngGetProjectDate(request.getProjectEndDate())
                : existingData.getProjectEndDate());
        existingData.setRemarks(request.getRemarks() != null ? validateAndGetRemarks(request.getRemarks())
                : existingData.getRemarks());

        projectRepository.save(existingData);
        return existingData;


    }

    public void sendEmailToEmployee(String toEmail, String body, String subject) {
        log.info("ProjectServiceImpl::sendEmailToEmployee");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ganga.tharan@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        javaMailSender.send(message);
        log.info("Mail sent successfully");

    }

    @Override
    public List<ProjectResponse> getProjectDetails(Long empId) {
        log.info("ProjectServiceImpl::getProjectDetails");
        List<ProjectResponse> projectDetails = projectRepository.findProjectsByEmpId(empId);
        return projectDetails.isEmpty() ? List.of() : projectDetails;
    }

     @Override
    public List<Project> getAllProjectDetails() {
        return projectRepository.findAll();
    }

    private static LocalDate validateAngGetProjectDate(LocalDate projectStartDate) throws ParseException {
        if (projectStartDate == null) {
            throw new ProjectCustomException("Enter Proper Date", "INVALID_DATE");
        } else {
            return projectStartDate;
        }
    }

    private static Float validateAndGetAllocation(Float allocation) {
        if (allocation == null || allocation <= 0.1f || allocation >= 1.0f) {
            throw new ProjectCustomException("Enter Proper Allocation value", "INVALID_ALLOCATION_VALUE");
        } else {
            return allocation;
        }
    }

    private static String validateAndGetRemarks(String remarks) {
        if (remarks == null || remarks.trim().isBlank()) {
            throw new ProjectCustomException("Enter Proper Remarks", "INVALID_REMARK");
        } else {
            return remarks;
        }
    }

    private static String validateAndGetAccountName(String accountName) {
        if (accountName == null || accountName.trim().isBlank()) {
            throw new ProjectCustomException("Enter Proper Account name", "INVALID_ACCOUNT_NAME");
        } else {
            return accountName;
        }
    }

    private static String validateAndGetProjectName(String projectName) {
        if (projectName == null || projectName.trim().isBlank()) {
            throw new ProjectCustomException("Enter Proper Project Name", "INVALID_PROJECT_NAME");
        } else {
            return projectName;
        }
    }

    private static Long validateAndGetEmpId(Long empID) {
        if (empID == null || empID <= 0) {
            throw new ProjectCustomException("Enter Proper Employee Id", "INVALID_EMPLOYEE_ID");
        } else {
            return empID;
        }
    }
}
