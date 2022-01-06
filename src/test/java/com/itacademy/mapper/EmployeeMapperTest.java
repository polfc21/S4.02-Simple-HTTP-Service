package com.itacademy.mapper;

import com.itacademy.dto.EmployeeDTO;
import com.itacademy.model.Employee;
import com.itacademy.model.JobType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class EmployeeMapperTest {

    private EmployeeMapper sut;

    @BeforeEach
    void setUp() {
        this.sut = new EmployeeMapper();
    }

    @Test
    void testGivenNullEmployeeDTOWhenGetEmployeeThenThrowsAssertionError() {
        Assertions.assertThrows(AssertionError.class, () -> this.sut.getEmployee(null));
    }

    @Test
    void testGivenCorrectEmployeeDTOWhenGetEmployeeThenReturnEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L,"Pol","junior", 1200D, null, null);

        assertThat(this.sut.getEmployee(employeeDTO), is(new Employee(employeeDTO)));
    }

    @Test
    void testGivenNullEmployeeWhenGetEmployeeDTOThenThrowsAssertionError() {
        Assertions.assertThrows(AssertionError.class, () -> this.sut.getEmployeeDTO(null));
    }

    @Test
    void testGivenCorrectEmployeeWhenGetEmployeeDTOThenReturnEmployeeDTO() {
        Employee employee = new Employee(1L,"Pol", JobType.OWNER, JobType.OWNER.getSalary());

        assertThat(this.sut.getEmployeeDTO(employee), is(new EmployeeDTO(employee)));
    }

    @Test
    void testGivenNullEmployeesWhenGetAllEmployeesDTOThenThrowsAssertionError() {
        Assertions.assertThrows(AssertionError.class, () -> this.sut.getAllEmployeesDTO(null));
    }


    @Test
    void testGivenCorrectEmployeesWhenGetAllEmployeesDTOThenReturnEmployeeDTOList() {
        Employee employee = new Employee(1L,"Pol",JobType.MANAGER, JobType.MANAGER.getSalary());
        List<Employee> employees = List.of(employee);

        assertThat(this.sut.getAllEmployeesDTO(employees), is(List.of(new EmployeeDTO(employee))));
    }
}