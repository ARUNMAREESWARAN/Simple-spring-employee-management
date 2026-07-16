package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.DepartmentDTO;
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
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // returns every department, converted to DTOs using map()
    public List<DepartmentDTO> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(department -> {
                    // build one DTO from one Department, right here
                    DepartmentDTO departmentDTO = new DepartmentDTO();
                    departmentDTO.setId(department.getId());
                    departmentDTO.setName(department.getName());
                    departmentDTO.setLocation(department.getLocation());
                    return departmentDTO;
                })
                .collect(Collectors.toList());
    }

    // returns one department by id, or null if not found
    public DepartmentDTO getDepartmentById(Long id) {

        Optional<Department> departmentOptional = departmentRepository.findById(id);

        if (!departmentOptional.isPresent()) {
            return null;
        }

        Department department = departmentOptional.get();

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        departmentDTO.setLocation(department.getLocation());

        return departmentDTO;
    }

    // creates a brand new department
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {

        Department newDepartment = new Department();
        newDepartment.setName(departmentDTO.getName());
        newDepartment.setLocation(departmentDTO.getLocation());

        Department savedDepartment = departmentRepository.save(newDepartment);

        DepartmentDTO savedDepartmentDTO = new DepartmentDTO();
        savedDepartmentDTO.setId(savedDepartment.getId());
        savedDepartmentDTO.setName(savedDepartment.getName());
        savedDepartmentDTO.setLocation(savedDepartment.getLocation());

        return savedDepartmentDTO;
    }

    // updates an existing department
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {

        Optional<Department> departmentOptional = departmentRepository.findById(id);

        if (!departmentOptional.isPresent()) {
            return null;
        }

        Department existingDepartment = departmentOptional.get();
        existingDepartment.setName(departmentDTO.getName());
        existingDepartment.setLocation(departmentDTO.getLocation());

        Department savedDepartment = departmentRepository.save(existingDepartment);

        DepartmentDTO savedDepartmentDTO = new DepartmentDTO();
        savedDepartmentDTO.setId(savedDepartment.getId());
        savedDepartmentDTO.setName(savedDepartment.getName());
        savedDepartmentDTO.setLocation(savedDepartment.getLocation());

        return savedDepartmentDTO;
    }

    // deletes a department, and unlinks its employees instead of deleting them
    public boolean deleteDepartment(Long id) {

        Optional<Department> departmentOptional = departmentRepository.findById(id);

        if (!departmentOptional.isPresent()) {
            return false;
        }

        Department departmentToDelete = departmentOptional.get();

        List<Employee> employeesInThisDepartment = employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartment() != null
                        && employee.getDepartment().getId().equals(id))
                .collect(Collectors.toList());

        for (Employee employee : employeesInThisDepartment) {
            employee.setDepartment(null);
            employeeRepository.save(employee);
        }

        departmentRepository.delete(departmentToDelete);
        return true;
    }

}