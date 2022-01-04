package com.itacademy.service;

import com.itacademy.dto.EmployeeDTO;
import com.itacademy.mapper.EmployeeMapper;
import com.itacademy.model.Employee;
import com.itacademy.model.JobType;
import com.itacademy.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeMapper.getAllEmployeesDTO(employeeList);
    }

    @Override
    public Optional<EmployeeDTO> getEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            EmployeeDTO employeeDTO = employeeMapper.getEmployeeDTO(employee);
            return Optional.of(employeeDTO);
        }

        return Optional.empty();
    }

    @Override
    public List<EmployeeDTO> getEmployeesByJobType(String jobType) {
        if (JobType.existsJob(jobType)) {
            List<Employee> employees =
                    employeeRepository.getEmployeesByJobType(JobType.valueOf(jobType.toUpperCase()));
            return employeeMapper.getAllEmployeesDTO(employees);
        }
        return null;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.getEmployee(employeeDTO);
        employeeRepository.save(employee);
        return employeeMapper.getEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<EmployeeDTO> employeeDTOOptional = this.getEmployee(id);
        if (employeeDTOOptional.isPresent()) {
            Employee updatedEmployee = employeeRepository.findById(id).get();
            updatedEmployee.setName(employeeDTO.getName());
            updatedEmployee.setJobType(JobType.valueOf(employeeDTO.getJobType().toUpperCase()));
            updatedEmployee.setSalary(updatedEmployee.getJobType().getSalary());
            employeeRepository.save(updatedEmployee);
            return employeeMapper.getEmployeeDTO(updatedEmployee);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
