package com.employeemanagement.project.controller;

import com.employeemanagement.project.dto.EmployeeDto;
import com.employeemanagement.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto getEmployeeById(@PathVariable int id) {
        return employeeService.findById(id);
    }

    @PostMapping("/employees")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.save(employeeDto);
    }

    @PutMapping("/employees/{id}")
    public EmployeeDto updateEmployee(@PathVariable int id,
                                      @RequestBody EmployeeDto employeeDto) {
        return employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
    }

    @GetMapping("/employees/search")
    public List<EmployeeDto> searchByDepartment(@RequestParam String department) {
        return employeeService.searchByDepartmentName(department);
    }

    @GetMapping("/departments/{id}/employees")
    public List<EmployeeDto> getEmployeesByDepartment(@PathVariable int id) {
        return employeeService.findEmployeesByDepartmentId(id);
    }

}