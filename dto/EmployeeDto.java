package com.employeemanagement.project.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDto {

    private int id;

    private String name;

    private String email;

    private double salary;

    private LocalDate joiningDate;

    private boolean isActive;

    private DepartmentDto department;

}