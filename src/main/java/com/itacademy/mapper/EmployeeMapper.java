package com.itacademy.mapper;

import com.itacademy.dto.EmployeeDTO;
import com.itacademy.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapper {

    public Employee getEmployee(EmployeeDTO employeeDTO) {
        /*
        if (employeeDTO == null) {
            return null;
        }*/
        assert employeeDTO != null;
        return new Employee(employeeDTO);
    }

    public EmployeeDTO getEmployeeDTO(Employee employee) {
        /*
        if (employee == null) {
            return null;
        }*/
        assert employee != null;
        return new EmployeeDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployeesDTO(List<Employee> employees) {
        /*
        if (employees == null) {
            return null;
        }*/
        assert employees != null;
        List<EmployeeDTO> employeeDTOList = new ArrayList<>(employees.size());
        for (Employee employee : employees) {
            employeeDTOList.add(getEmployeeDTO(employee));
        }
        return employeeDTOList;
    }

}
