package com.itacademy.repository;

import com.itacademy.model.Employee;
import com.itacademy.model.JobType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final Employee JUNIOR = new Employee("Junior", JobType.JUNIOR);
    private final Employee MIDDLE = new Employee("Middle", JobType.MIDDLE);
    private final Employee SENIOR = new Employee("Senior", JobType.SENIOR);
    private final Employee MANAGER = new Employee("Manager", JobType.MANAGER);
    private final Employee OWNER = new Employee("Owner", JobType.OWNER);


    @BeforeEach
    private void setUpBefore() {
        employeeRepository.save(JUNIOR);
        employeeRepository.save(MIDDLE);
        employeeRepository.save(SENIOR);
        employeeRepository.save(MANAGER);
        employeeRepository.save(OWNER);
    }

    @Test
    void testWhenFindByJuniorJobType_thenReturnJuniors() {
        List<Employee> employees = employeeRepository.getEmployeesByJobType(JobType.JUNIOR);
        Assertions.assertThat(employees)
                .contains(JUNIOR)
                .doesNotContain(MIDDLE)
                .doesNotContain(SENIOR)
                .doesNotContain(MANAGER)
                .doesNotContain(OWNER);
    }

    @Test
    void whenFindByMiddleJobType_thenReturnMiddles() {
        List<Employee> employees = employeeRepository.getEmployeesByJobType(JobType.MIDDLE);
        Assertions.assertThat(employees)
                .contains(MIDDLE)
                .doesNotContain(JUNIOR)
                .doesNotContain(SENIOR)
                .doesNotContain(MANAGER)
                .doesNotContain(OWNER);
    }

    @Test
    void whenFindBySeniorJobType_thenReturnSeniors() {
        List<Employee> employees = employeeRepository.getEmployeesByJobType(JobType.SENIOR);
        Assertions.assertThat(employees)
                .contains(SENIOR)
                .doesNotContain(JUNIOR)
                .doesNotContain(MIDDLE)
                .doesNotContain(MANAGER)
                .doesNotContain(OWNER);
    }

    @Test
    void whenFindByManagerJobType_thenReturnManagers() {
        List<Employee> employees = employeeRepository.getEmployeesByJobType(JobType.MANAGER);
        Assertions.assertThat(employees)
                .contains(MANAGER)
                .doesNotContain(JUNIOR)
                .doesNotContain(MIDDLE)
                .doesNotContain(SENIOR)
                .doesNotContain(OWNER);
    }

    @Test
    void whenFindByOwnerJobType_thenReturnOwners() {
        List<Employee> employees = employeeRepository.getEmployeesByJobType(JobType.OWNER);
        Assertions.assertThat(employees)
                .contains(OWNER)
                .doesNotContain(JUNIOR)
                .doesNotContain(MIDDLE)
                .doesNotContain(SENIOR)
                .doesNotContain(MANAGER);
    }
}
