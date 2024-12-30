package com.project.allocation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.allocation.entity.Project;
import com.project.allocation.model.ProjectRequest;
import com.project.allocation.model.ProjectResponse;
import com.project.allocation.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    public void addProjectTest() throws Exception {
        ProjectRequest request = ProjectRequest.builder()
                .projectName("New Project")
                .accountName("account1")
                .allocation(0.5F)
                .remarks("New project for development")
                .build();
        Project project = Project.builder()
                .projectId(1L)
                .projectName("New Project")
                .accountName("account1")
                .allocation(0.5F)
                .remarks("New project for development")
                .build();
        when(projectService.addProjectDetails("test@example.com", request)).thenReturn(project);
        MvcResult result = mockMvc.perform(post("/project/add")
                        .param("email", "test@example.com")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("PROJECT_DETAILS"))
                .andExpect(jsonPath("$.data.projectId").value(1))
                .andExpect(jsonPath("$.data.accountName").value("account1"))
                .andExpect(jsonPath("$.data.projectName").value("New Project"))
                .andExpect(jsonPath("$.data.remarks").value("New project for development"))
                .andReturn();
    }

    @Test
    public void getProjectDetailsTest() throws Exception {
        ProjectResponse response = ProjectResponse.builder()
                .projectId(1L)
                .projectName("New Project")
                .accountName("account1")
                .allocation(0.5F)
                .remarks("New project for development")
                .build();
        List<ProjectResponse> projectList = Arrays.asList(response);
        when(projectService.getProjectDetails(1L)).thenReturn(projectList);

        mockMvc.perform(get("/project/get/{empId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].projectId").value(1))
                .andExpect(jsonPath("$.[0].accountName").value("account1"))
                .andExpect(jsonPath("$.[0].projectName").value("New Project"))
                .andExpect(jsonPath("$.[0].allocation").value(0.5))
                .andExpect(jsonPath("$.[0].remarks").value("New project for development"))
                .andReturn();
    }

}
