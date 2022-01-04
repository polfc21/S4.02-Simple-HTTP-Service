package com.itacademy.model;


import com.itacademy.dto.EmployeeDTO;
import lombok.*;

import javax.persistence.*;



@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    @NonNull
    private JobType jobType;

    @NonNull
    private double salary;

    public Employee(EmployeeDTO employeeDTO) {
        this.name = employeeDTO.getName();
        this.jobType = JobType.valueOf(employeeDTO.getJobType().toUpperCase());
        this.salary = this.jobType.getSalary();
    }

    public Employee(String name, JobType jobType) {
        this.name = name;
        this.jobType = jobType;
        this.salary = this.jobType.getSalary();
    }

}
