package com.employee.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long  employeeId;

    @Column(name = "emp_name")
    private String employeeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "capability_center")
    private CapabilityCentre capabilityCentre;

    @Column(name = "data_of_joining")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfJoining;

    @Enumerated(EnumType.STRING)
    @Column(name = "designation")
    private Designation designation;

    @Column(name = "primary_skill")
    private String primarySkill;

    @Column(name = "secondary_skill")
    private String secondarySkill;

    @Column(name = "over_all_experience")
    private Double overallExperience;


}
