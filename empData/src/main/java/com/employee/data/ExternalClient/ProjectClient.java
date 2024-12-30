package com.employee.data.ExternalClient;

import com.employee.data.model.ApiResponse;
import com.employee.data.model.ProjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name ="PROJECT.ALLOCATION", url = "http://localhost:8081/project")
public interface ProjectClient {
    @GetMapping("/get/{empId}")
    public ResponseEntity<List<ProjectResponse>> getProjectDetails(@PathVariable Long empId);
}
