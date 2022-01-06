package com.itacademy.dto;

import com.itacademy.model.Employee;
import com.itacademy.validators.Job;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {


    @ApiModelProperty(notes = "Unique identifier of the User.", example = "1", required = false, position = 0)
    private Long id;

    @NonNull
    @NotBlank(message = "Job type should not be blank")
    private String name;

    @NonNull
    @NotBlank(message = "Job type should not be blank")
    @ApiModelProperty(example = "Junior, Middle, Senior, Manager, Owner")
    @Job
    private String jobType;

    private Double salary;

    private String photoName;

    private String type;

    public EmployeeDTO (Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.jobType = employee.getJobType().toString();
        this.salary = employee.getSalary();
        this.photoName = employee.getPhotoName();
        this.type = employee.getType();
    }

}
