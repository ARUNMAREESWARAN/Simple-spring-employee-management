package com.employeemanagement.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Employee {

    @Id
    private int id;

    private String name;

    private String email;

    private double salary;

    private LocalDate joiningDate;

    private boolean isActive;

    @ManyToOne
    private Department department;

}