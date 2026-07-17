package com.employeemanagement.project.repository;

import com.employeemanagement.project.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByDepartment_Name(String department);

    List<Employee> findByDepartmentId(int id);

}