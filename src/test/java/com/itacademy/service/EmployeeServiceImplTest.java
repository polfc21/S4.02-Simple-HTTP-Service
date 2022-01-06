package com.itacademy.service;

import com.itacademy.dto.EmployeeDTO;
import com.itacademy.mapper.EmployeeMapper;
import com.itacademy.model.Employee;
import com.itacademy.model.JobType;
import com.itacademy.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl sut;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        this.sut = new EmployeeServiceImpl(employeeRepository, employeeMapper);
    }

    @Test
    void testWhenGetAllEmployeesThenReturnEmployeesDTO() {
        List<Employee> employees = List.of(new Employee());
        List<EmployeeDTO> employeesDTO = List.of(new EmployeeDTO());

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeMapper.getAllEmployeesDTO(employees)).thenReturn(employeesDTO);

        assertThat(sut.getAllEmployees(), is(employeesDTO));
    }

    @Test
    void testGivenPresentIdWhenGetOneEmployeeThenReturnOptionalEmployeeDTO() {
        long presentId = 0;
        Employee employee = new Employee();
        EmployeeDTO employeeDTO = new EmployeeDTO();

        when(employeeRepository.findById(presentId)).thenReturn(Optional.of(employee));
        when(employeeMapper.getEmployeeDTO(employee)).thenReturn(employeeDTO);

        assertThat(sut.getEmployee(presentId), is(Optional.of(employeeDTO)));
    }

    @Test
    void testGivenNotPresentIdWhenGetOneEmployeeThenReturnOptionalEmpty() {
        long notPresentId = 0;

        when(employeeRepository.findById(notPresentId)).thenReturn(Optional.empty());

        verify(employeeMapper, never()).getEmployeeDTO(null);
        assertThat(sut.getEmployee(notPresentId), is(Optional.empty()));
    }

    @Test
    void testGivenCorrectJobTypeWhenGetEmployeesByJobTypeThenReturnEmployees() {
        List<Employee> employees = List.of(new Employee());
        List<EmployeeDTO> employeesDTO = List.of(new EmployeeDTO());

        when(employeeRepository.getEmployeesByJobType(JobType.JUNIOR)).thenReturn(employees);
        when(employeeMapper.getAllEmployeesDTO(employees)).thenReturn(employeesDTO);

        assertThat(this.sut.getEmployeesByJobType("JUNIOR"), is(employeesDTO));
    }

    @Test
    void testGivenIncorrectJobTypeWhenGetEmployeesByJobTypeThenReturnNull() {
        verify(employeeRepository, never()).getEmployeesByJobType(JobType.OWNER);
        verify(employeeMapper, never()).getAllEmployeesDTO(List.of());
        Assertions.assertNull(this.sut.getEmployeesByJobType("NULL"));
    }

    @Test
    void testGivenEmployeeDTOWhenCreateEmployeeThenReturnEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Employee employee = new Employee();

        when(employeeMapper.getEmployee(employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.getEmployeeDTO(employee)).thenReturn(employeeDTO);

        assertThat(this.sut.createEmployee(employeeDTO), is(employeeDTO));
    }

    @Test
    void testGivenPresentIdWhenUpdateEmployeeThenReturnEmployeeDTOUpdated() {
        long presentId = 0;
        Employee employee = new Employee();
        EmployeeDTO employeeDTO = new EmployeeDTO(presentId, "Pol","JUNIOR",1200D, null, null);
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();

        when(sut.getEmployee(presentId)).thenReturn(Optional.of(employeeDTO));
        when(employeeRepository.findById(presentId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.getEmployeeDTO(employee)).thenReturn(updatedEmployeeDTO);

        assertThat(sut.updateEmployee(presentId, employeeDTO), is(updatedEmployeeDTO));
    }

    @Test
    void testGivenNotPresentIdWhenUpdateEmployeeThenReturnNull() {
        long notPresentId = 0;

        when(sut.getEmployee(notPresentId)).thenReturn(Optional.empty());

        verify(employeeRepository, never()).findById(notPresentId);
        verify(employeeRepository, never()).save(null);
        verify(employeeMapper, never()).getEmployeeDTO(null);
        Assertions.assertNull(this.sut.updateEmployee(notPresentId, null));
    }

    @Test
    void testGivenIdWhenDeleteEmployeeThenDeleteById() {
        Long id = 0L;

        this.sut.deleteEmployee(id);

        verify(employeeRepository).deleteById(id);
    }

    @Test
    void testGivenCorrectIdWhenUploadPhotoEmployeeThenReturnEmployeeDTO() throws IOException {
        long presentId = 0;
        Employee employee = new Employee();
        EmployeeDTO employeeDTO = new EmployeeDTO(presentId, "Pol","JUNIOR",1200D, null, null);
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        MockMultipartFile photo = new MockMultipartFile("photo", new byte[5]);

        when(sut.getEmployee(presentId)).thenReturn(Optional.of(employeeDTO));
        when(employeeRepository.findById(presentId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.getEmployeeDTO(employee)).thenReturn(updatedEmployeeDTO);

        assertThat(sut.uploadPhotoEmployee(presentId, photo), is(updatedEmployeeDTO));
    }

    @Test
    void testGivenNotPresentIdWhenUploadPhotoEmployeeThenReturnNull() throws IOException {
        long notPresentId = 0;

        when(sut.getEmployee(notPresentId)).thenReturn(Optional.empty());

        verify(employeeRepository, never()).findById(notPresentId);
        verify(employeeRepository, never()).save(null);
        verify(employeeMapper, never()).getEmployeeDTO(null);
        Assertions.assertNull(this.sut.uploadPhotoEmployee(notPresentId, null));
    }

    @Test
    void testGivenPresentIdWhenGetPhotoNameEmployeeThenReturnString() {
        long presentId = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO(presentId,"Pol","Junior",1200D,"photoTest","image/jpeg");
        Employee employee = new Employee(employeeDTO);

        when(sut.getEmployee(presentId)).thenReturn(Optional.of(employeeDTO));
        when(employeeRepository.findById(presentId)).thenReturn(Optional.of(employee));
        when(employeeMapper.getEmployeeDTO(employee)).thenReturn(employeeDTO);

        assertThat(sut.getPhotoNameEmployee(presentId), is(employeeDTO.getPhotoName()));
    }

    @Test
    void testGivenNotPresentIdWhenGetPhotoNameEmployeeThenReturnNull() {
        long notPresentId = 0;

        when(sut.getEmployee(notPresentId)).thenReturn(Optional.empty());

        verify(employeeRepository, never()).findById(notPresentId);
        verify(employeeMapper, never()).getEmployeeDTO(null);
        Assertions.assertNull(this.sut.getPhotoNameEmployee(notPresentId));
    }
}
