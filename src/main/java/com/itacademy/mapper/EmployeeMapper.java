package com.itacademy.mapper;

import com.itacademy.dto.EmployeeDTO;
import com.itacademy.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapper {

    public Employee getEmployee(EmployeeDTO employeeDTO) {
        assert employeeDTO != null;
        return new Employee(employeeDTO);
    }

    public EmployeeDTO getEmployeeDTO(Employee employee) {
        assert employee != null;
        return new EmployeeDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployeesDTO(List<Employee> employees) {
        assert employees != null;
        List<EmployeeDTO> employeeDTOList = new ArrayList<>(employees.size());
        for (Employee employee : employees) {
            employeeDTOList.add(getEmployeeDTO(employee));
        }
        return employeeDTOList;
    }

}
