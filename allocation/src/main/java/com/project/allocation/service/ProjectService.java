package com.project.allocation.service;

import com.project.allocation.entity.Project;
import com.project.allocation.model.ProjectRequest;
import com.project.allocation.model.ProjectResponse;

import java.text.ParseException;
import java.util.List;

public interface ProjectService {
    Project addProjectDetails(String email,ProjectRequest projectRequest) throws ParseException;

    Project updateProjectDetails(Long projectId, ProjectRequest request) throws ParseException;

    List<ProjectResponse> getProjectDetails(Long empId);
}
