package com.project.allocation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    private Long empId;
    private String accountName;
    private String projectName;
    private Float allocation;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate projectStartDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate projectEndDate;
    private String remarks;
}
