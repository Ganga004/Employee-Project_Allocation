package com.project.allocation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long projectId;
    private Long empId;
    private String accountName;
    private String projectName;
    private Float allocation;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String remarks;
}
