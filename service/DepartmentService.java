package com.employeemanagement.project.service;

import com.employeemanagement.project.dto.DepartmentDto;
import com.employeemanagement.project.entity.Department;
import com.employeemanagement.project.repository.DepartmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<DepartmentDto> findAll() {

        List<Department> departments = departmentRepository.findAll();

        return departments.stream()
                .map(department -> objectMapper.convertValue(department, DepartmentDto.class))
                .toList();
    }

    public DepartmentDto findById(int id) {

        Department department = departmentRepository.findById(id).orElse(null);

        if (department == null) {
            return null;
        }

        return objectMapper.convertValue(department, DepartmentDto.class);
    }

    public DepartmentDto save(DepartmentDto departmentDto) {

        Department department = objectMapper.convertValue(departmentDto, Department.class);

        Department savedDepartment = departmentRepository.save(department);

        return objectMapper.convertValue(savedDepartment, DepartmentDto.class);
    }

    public DepartmentDto updateDepartment(int id, DepartmentDto departmentDto) {

        Department department = objectMapper.convertValue(departmentDto, Department.class);

        department.setId(id);

        Department updatedDepartment = departmentRepository.save(department);

        return objectMapper.convertValue(updatedDepartment, DepartmentDto.class);
    }

    public void deleteDepartment(int id) {

        departmentRepository.deleteById(id);
    }

}