package com.itacademy.repository;

import com.itacademy.model.Employee;
import com.itacademy.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> getEmployeesByJobType(JobType jobType);
}
