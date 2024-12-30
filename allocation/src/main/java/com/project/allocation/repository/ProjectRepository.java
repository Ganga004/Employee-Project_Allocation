package com.project.allocation.repository;

import com.project.allocation.entity.Project;
import com.project.allocation.model.ProjectResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT COUNT(*) FROM Project p WHERE p.empId = :empId")
    Integer countOfEmployeeProject(Long empId);

//    List<Project> findByEmpId(Long empId);
    @Query("SELECT new com.project.allocation.model.ProjectResponse(p.projectId, p.empId, p.accountName, " +
            "p.projectName, p.allocation, p.projectStartDate, p.projectEndDate, p.remarks) FROM Project p " +
            "WHERE p.empId = :empId")
    List<ProjectResponse> findProjectsByEmpId(@Param("empId") Long empId);
}
