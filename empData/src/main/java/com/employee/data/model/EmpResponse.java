package com.employee.data.model;

import com.employee.data.entity.CapabilityCentre;
import com.employee.data.entity.Designation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpResponse {
    private Long  employeeId;
    private String employeeName;
    private CapabilityCentre capabilityCentre;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfJoining;
    private Designation designation;
    private String primarySkill;
    private String secondarySkill;
    private Double overallExperience;
    private List<ProjectResponse> projectDetailsResponse;
}
