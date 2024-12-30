package com.project.allocation.serviceImpl;

import com.project.allocation.entity.Project;
import com.project.allocation.exception.ProjectCustomException;
import com.project.allocation.model.ProjectRequest;
import com.project.allocation.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private ProjectServiceImpl projectServiceImpl;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sendMailToEmployeeTest() {
        projectServiceImpl.sendEmailToEmployee("raja@email.com", "Mail content", "Mail Subject");
    }

    @Test
    public void getProjectDetailsWithEmployeeListTtest() {
        Mockito.when(projectRepository.findProjectsByEmpId(any())).thenReturn(anyList());
        projectServiceImpl.getProjectDetails(2L);
    }

    @Test
    public void getProjectDetailsWithEmptyEmployeeListTtest() {
        Mockito.when(projectRepository.findProjectsByEmpId(any())).thenReturn(List.of());
        projectServiceImpl.getProjectDetails(2L);
    }

    @Test
    public void addProjectDetailsTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(2L).projectName("Citi").accountName("Citibank")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.6F).remarks("Good Work").build();
        Mockito.when(projectRepository.countOfEmployeeProject(anyLong())).thenReturn(1);
        projectServiceImpl.addProjectDetails("raja@mail.com", request);
    }

    @Test
    public void addProjectDetailsWithExceptionTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(2L).projectName("Citi").accountName("Citibank")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.6F).remarks("Good Work").build();
        Mockito.when(projectRepository.countOfEmployeeProject(anyLong())).thenReturn(3);
        Assertions.assertThrows((ProjectCustomException.class), () -> projectServiceImpl.addProjectDetails("raja@mail.com", request));
    }


    @Test
    public void updateProjectDetailsTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(2L).projectName("Citi").accountName("Citibank")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.6F).remarks("Good Work").build();
        Project project = Project.builder().empId(3L).projectName("Ford").accountName("zeta")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.4F).remarks("Nice job").build();
        Mockito.when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(project));
        projectServiceImpl.updateProjectDetails(4L, request);
    }

    @Test
    public void updateProjectDetailsWithInvalidEmpIdTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(-2L).build();
        Project project = Project.builder().empId(3L).projectName("Ford").accountName("zeta")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.4F).remarks("Nice job").build();
        Mockito.when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(project));
        Assertions.assertThrows(ProjectCustomException.class, () -> projectServiceImpl.updateProjectDetails(4L, request));
    }

    @Test
    public void updateProjectDetailsWithEmpIdAsNullTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(null).build();
        Project project = Project.builder().empId(3L).projectName("Ford").accountName("zeta")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.4F).remarks("Nice job").build();
        Mockito.when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(project));
        projectServiceImpl.updateProjectDetails(4L, request);
    }

    @Test
    public void updateProjectDetailsWithInvalidProjectNameTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(2L).projectName(" ").build();
        Project project = Project.builder().empId(3L).projectName("Ford").accountName("zeta")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.4F).remarks("Nice job").build();
        Mockito.when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(project));
        Assertions.assertThrows(ProjectCustomException.class, () -> projectServiceImpl.updateProjectDetails(4L, request));
    }

    @Test
    public void updateProjectDetailsWithInvalidAccountNameTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(2L).projectName("Ford").accountName(" ").build();
        Project project = Project.builder().empId(3L).projectName("Ford").accountName("zeta")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.4F).remarks("Nice job").build();
        Mockito.when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(project));
        Assertions.assertThrows(ProjectCustomException.class, () -> projectServiceImpl.updateProjectDetails(4L, request));
    }

    @Test
    public void updateProjectDetailsWithInvalidRemarksTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(2L).projectName("Citi").accountName("Citibank")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.6F).remarks(" ").build();
        Project project = Project.builder().empId(3L).projectName("Ford").accountName("zeta")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.4F).remarks("Nice job").build();
        Mockito.when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(project));
        Assertions.assertThrows(ProjectCustomException.class, () -> projectServiceImpl.updateProjectDetails(4L, request));
    }

    @Test
    public void updateProjectDetailsWithInvalidAllocationTest() throws ParseException {
        ProjectRequest request = ProjectRequest.builder().empId(2L).projectName("Citi").accountName("Citibank")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(1.6F).build();
        Project project = Project.builder().empId(3L).projectName("Ford").accountName("zeta")
                .projectStartDate(LocalDate.now()).projectEndDate(LocalDate.now()).allocation(0.4F).remarks("Nice job").build();
        Mockito.when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(project));
        Assertions.assertThrows(ProjectCustomException.class, () -> projectServiceImpl.updateProjectDetails(4L, request));
    }


}
