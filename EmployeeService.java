package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // small private helper just for turning an Employee into an EmployeeDTO
    // used inside map() wherever we need to convert a list
    private EmployeeDTO toDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setJoiningDate(employee.getJoiningDate());
        employeeDTO.setActive(employee.isActive());

        if (employee.getDepartment() != null) {
            employeeDTO.setDepartmentId(employee.getDepartment().getId());
            employeeDTO.setDepartmentName(employee.getDepartment().getName());
        }

        return employeeDTO;
    }

    // returns every employee, converted to DTOs using map()
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // returns one employee by id, or null if not found
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (!employeeOptional.isPresent()) {
            return null;
        }

        return toDto(employeeOptional.get());
    }

    // creates a brand new employee
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        Employee newEmployee = new Employee();
        newEmployee.setName(employeeDTO.getName());
        newEmployee.setEmail(employeeDTO.getEmail());
        newEmployee.setSalary(employeeDTO.getSalary());
        newEmployee.setJoiningDate(employeeDTO.getJoiningDate());
        newEmployee.setActive(employeeDTO.isActive());

        Long givenDepartmentId = employeeDTO.getDepartmentId();

        if (givenDepartmentId != null) {
            Optional<Department> departmentOptional = departmentRepository.findById(givenDepartmentId);

            if (departmentOptional.isPresent()) {
                newEmployee.setDepartment(departmentOptional.get());
            }
        }

        Employee savedEmployee = employeeRepository.save(newEmployee);

        return toDto(savedEmployee);
    }

    // updates an existing employee
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (!employeeOptional.isPresent()) {
            return null;
        }

        Employee existingEmployee = employeeOptional.get();
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setJoiningDate(employeeDTO.getJoiningDate());
        existingEmployee.setActive(employeeDTO.isActive());

        Long givenDepartmentId = employeeDTO.getDepartmentId();

        if (givenDepartmentId != null) {
            Optional<Department> departmentOptional = departmentRepository.findById(givenDepartmentId);

            if (departmentOptional.isPresent()) {
                existingEmployee.setDepartment(departmentOptional.get());
            }
        } else {
            existingEmployee.setDepartment(null);
        }

        Employee savedEmployee = employeeRepository.save(existingEmployee);

        return toDto(savedEmployee);
    }

    // deletes an employee by id
    public boolean deleteEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (!employeeOptional.isPresent()) {
            return false;
        }

        employeeRepository.delete(employeeOptional.get());
        return true;
    }

    // finds every employee whose department name matches, using map()
    public List<EmployeeDTO> searchByDepartmentName(String departmentName) {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartment() != null
                        && employee.getDepartment().getName().equalsIgnoreCase(departmentName))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // finds every employee whose department id matches, using map()
    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartment() != null
                        && employee.getDepartment().getId().equals(departmentId))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}