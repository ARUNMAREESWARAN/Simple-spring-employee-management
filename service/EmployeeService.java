package com.employeemanagement.project.service;

import com.employeemanagement.project.dto.EmployeeDto;
import com.employeemanagement.project.entity.Employee;
import com.employeemanagement.project.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<EmployeeDto> findAll() {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(employee -> objectMapper.convertValue(employee, EmployeeDto.class))
                .toList();
    }

    public EmployeeDto findById(int id) {

        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            return null;
        }

        return objectMapper.convertValue(employee, EmployeeDto.class);
    }

    public EmployeeDto save(EmployeeDto employeeDto) {

        Employee employee = objectMapper.convertValue(employeeDto, Employee.class);

        Employee savedEmployee = employeeRepository.save(employee);

        return objectMapper.convertValue(savedEmployee, EmployeeDto.class);
    }

    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {

        Employee employee = objectMapper.convertValue(employeeDto, Employee.class);

        employee.setId(id);

        Employee updatedEmployee = employeeRepository.save(employee);

        return objectMapper.convertValue(updatedEmployee, EmployeeDto.class);
    }

    public void deleteById(int id) {

        employeeRepository.deleteById(id);
    }

    public List<EmployeeDto> searchByDepartmentName(String department) {

        List<Employee> employees =
                employeeRepository.findByDepartment_Name(department);

        return employees.stream()
                .map(employee -> objectMapper.convertValue(employee, EmployeeDto.class))
                .toList();
    }

    public List<EmployeeDto> findEmployeesByDepartmentId(int id) {

        List<Employee> employees =
                employeeRepository.findByDepartmentId(id);

        return employees.stream()
                .map(employee -> objectMapper.convertValue(employee, EmployeeDto.class))
                .toList();
    }

}