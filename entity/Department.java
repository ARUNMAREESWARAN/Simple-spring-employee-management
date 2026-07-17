package com.employeemanagement.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Department {

    @Id
    private int id;

    private String name;

    private String location;

}