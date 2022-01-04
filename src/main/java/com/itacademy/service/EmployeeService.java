package com.itacademy.service;

import com.itacademy.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    Optional<EmployeeDTO> getEmployee(Long id);

    List<EmployeeDTO> getEmployeesByJobType(String jobType);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);

}
