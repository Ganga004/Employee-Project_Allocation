package com.project.allocation.controller;

import com.project.allocation.entity.Project;
import com.project.allocation.model.ApiResponse;
import com.project.allocation.model.ProjectRequest;
import com.project.allocation.model.ProjectResponse;
import com.project.allocation.service.ProjectService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProject(@RequestParam String email, @RequestBody ProjectRequest projectRequest) throws ParseException {
        Project project = projectService.addProjectDetails(email, projectRequest);
        return new ResponseEntity<>(new ApiResponse("PROJECT_DETAILS", project), HttpStatus.CREATED);
    }


    @PutMapping("/update/{projectId}")
    public ResponseEntity<ApiResponse> updateProjectDetails(@PathVariable Long projectId, @NonNull
                                            @RequestBody ProjectRequest request) throws ParseException {
    Project response = projectService.updateProjectDetails(projectId, request);
        return new ResponseEntity<>(new ApiResponse("PROJECT_DETAILS_UPDATED", response), HttpStatus.OK);
    }


    @GetMapping("/get/{empId}")
    public ResponseEntity<List<ProjectResponse>> getProjectDetails(@PathVariable Long empId) {
        List<ProjectResponse> listOfProjects = projectService.getProjectDetails(empId);
        return new ResponseEntity<>(listOfProjects,HttpStatus.OK) ;
    }

}
