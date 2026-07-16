package com.example.employeemanagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {

    private Long id;

    private String name;

    private String email;

    private double salary;

    private LocalDate joiningDate;

    private boolean isActive;

    private Long departmentId;

    private String departmentName;

}