package com.employeemanagement.project.repository;

import com.employeemanagement.project.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}