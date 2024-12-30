package com.project.allocation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name ="project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "allocation")
    private Float allocation;

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate projectStartDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate projectEndDate;

    @Column(name = "remarks")
    private String remarks;
}
