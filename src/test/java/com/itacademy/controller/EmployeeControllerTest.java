package com.itacademy.controller;

import com.itacademy.dto.EmployeeDTO;
import com.itacademy.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @MockBean
    private EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<EmployeeDTO>> jsonEmployeeDTOList;

    @Autowired
    private JacksonTester<EmployeeDTO> jsonEmployeeDTO;

    @Test
    void testGivenSavedEmployeesWhenGetAllEmployeesThenReturnOK() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L,"Pol","Junior",1200D);
        List<EmployeeDTO> employeeDTOList = List.of(employeeDTO);
        given(employeeServiceImpl.getAllEmployees()).willReturn(employeeDTOList);

        MockHttpServletResponse response = mvc.perform(
                get("/employees/all")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(jsonEmployeeDTOList.write(employeeDTOList).getJson()));
    }

    @Test
    void testGivenNonSavedEmployeesWhenGetAllEmployeesThenReturnNoContent() throws Exception {
        List<EmployeeDTO> employeeDTOList = List.of();
        given(employeeServiceImpl.getAllEmployees()).willReturn(employeeDTOList);

        MockHttpServletResponse response = mvc.perform(
                get("/employees/all")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }


    @Test
    void testGivenIdSavedEmployeeWhenGetEmployeeByIdThenReturnOk() throws Exception {
        Long correctId = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO(correctId, "Pol", "Junior", 1200D);
        given(employeeServiceImpl.getEmployee(correctId)).willReturn(Optional.of(employeeDTO));

        MockHttpServletResponse response = mvc.perform(
                get("/employees/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(jsonEmployeeDTO.write(employeeDTO).getJson()));
    }

    @Test
    void testGivenIdNonSavedEmployeeWhenGetEmployeeByIdThenReturnNotFound() throws Exception {
        Long incorrectId = 1L;
        given(employeeServiceImpl.getEmployee(incorrectId)).willReturn(Optional.empty());

        MockHttpServletResponse response = mvc.perform(
                get("/employees/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenJobTypeCorrectAndNotEmptyWhenGetEmployeeByJobTypeThenReturnNoContent() throws Exception {
        String correctJob = "junior";
        List<EmployeeDTO> employeeDTOList = List.of(new EmployeeDTO(1L, "Pol", correctJob, 1200D));
        given(employeeServiceImpl.getEmployeesByJobType(correctJob)).willReturn(employeeDTOList);

        MockHttpServletResponse response = mvc.perform(
                get("/employees/jobType/junior")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(jsonEmployeeDTOList.write(employeeDTOList).getJson()));
    }

    @Test
    void testGivenJobTypeCorrectAndEmptyWhenGetEmployeeByJobTypeThenReturnNoContent() throws Exception {
        String correctJob = "junior";
        given(employeeServiceImpl.getEmployeesByJobType(correctJob)).willReturn(List.of());

        MockHttpServletResponse response = mvc.perform(
                get("/employees/jobType/junior")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void testGivenJobTypeIncorrectWhenGetEmployeeByJobTypeThenReturnNoContent() throws Exception {
        String incorrectJob = "NULL";
        given(employeeServiceImpl.getEmployeesByJobType(incorrectJob)).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                get("/employees/jobType/null")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void testGivenCorrectEmployeeDTOWhenCreateEmployeeThenReturnCreated() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L,"Pol","Junior",1200D);
        given(employeeServiceImpl.createEmployee(employeeDTO)).willReturn(employeeDTO);

        MockHttpServletResponse response = mvc.perform(
                post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonEmployeeDTO.write(employeeDTO).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    void testGivenIncorrectEmployeeDTOWhenCreateEmployeeThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("BAD REQUEST"))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenCorrectEmployeeDTOWhenUpdateEmployeeThenReturnOk() throws Exception {
        Long presentId = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO(presentId,"Pol","Junior",1200D);
        given(employeeServiceImpl.updateEmployee(presentId, employeeDTO)).willReturn(employeeDTO);

        MockHttpServletResponse response = mvc.perform(
                put("/employees/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonEmployeeDTO.write(employeeDTO).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    void testGivenIncorrectEmployeeDTOWhenUpdateEmployeeThenReturnNotFound() throws Exception {
        Long notPresentId = 1L;
        given(employeeServiceImpl.updateEmployee(notPresentId, null)).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                put("/employees/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonEmployeeDTO.write(new EmployeeDTO(notPresentId,"Null","Null",0D)).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenIncorrectRequestWhenUpdateEmployeeThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmployeeDTO.write(new EmployeeDTO()).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenCorrectEmployeeIdWhenDeleteEmployeeThenReturnNoContent() throws Exception {
        MockHttpServletResponse response = mvc.perform(
            delete("/employees/1")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void testGivenIncorrectEmployeeIdWhenDeleteEmployeeThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
            delete("/employees/BAD_ID")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
