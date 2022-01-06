package com.itacademy.controller;

import com.itacademy.dto.EmployeeDTO;
import com.itacademy.service.EmployeeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Api(tags = "Employee API Rest")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    @ApiOperation(notes = "Retrieve all employees", value = "Get all employees")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Response ok if the operation was successful"),
                            @ApiResponse(code = 204, message = "Response no content if the resource could not be found")})
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeesDTO = employeeService.getAllEmployees();
        if (employeesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employeesDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(notes = "Retrieve one employee by id", value = "Get employee by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Response ok if the operation was successful"),
                            @ApiResponse(code = 404, message = "Response not found if the resource could not be found")})
    public ResponseEntity<EmployeeDTO> getEmployeeById(@ApiParam(example = "1", value = "Identifier for User", allowableValues = "1,2,3,4", required = true)
                                                        @PathVariable Long id) {
        Optional<EmployeeDTO> employeeDTOOptional = employeeService.getEmployee(id);
        try {
            EmployeeDTO employeeDTO = employeeDTOOptional.orElseThrow(NoSuchElementException::new);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/jobType/{jobType}")
    @ApiOperation(notes = "Retrieve all employees by Job Type", value = "Get all employees by Job Type")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Response ok if the operation was successful"),
                            @ApiResponse(code = 204, message = "Response no content if the resource could not be found")})
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByJobType(@ApiParam(example = "Junior", value = "Job Type",
                                                                        allowableValues = "Junior, Middle, Senior, Manager, Owner", required = true)
                                                                        @PathVariable String jobType) {
        List<EmployeeDTO> employeesDTO = employeeService.getEmployeesByJobType(jobType);
        if (employeesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employeesDTO, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(notes = "Retrieve all employees by Job Type", value = "Get all employees by Job Type")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Response ok if the operation was successful"),
                            @ApiResponse(code = 400, message = "Response bad request if the resource is not correct")})
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(notes = "Update employee by id", value = "Update employee by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Response ok if the operation was successful"),
                            @ApiResponse(code = 400, message = "Response bad request if the resource is not correct")})
    public ResponseEntity<EmployeeDTO> updateEmployee(@ApiParam(example = "1L", value = "Identifier for the user",
                                                            allowableValues = "1,2,3,4", required = true)
                                                            @PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO updateEmployeeDTO = employeeService.updateEmployee(id, employeeDTO);
            if (updateEmployeeDTO == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updateEmployeeDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(notes = "Delete employee by id", value = "Delete employee by id")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Response no content if the operation was successful"),
                            @ApiResponse(code = 400, message = "Response bad request if the resource is not correct")})
    public ResponseEntity<HttpStatus> deleteEmployee(@ApiParam(example = "1L", value = "Identifier for the user",
                                                         allowableValues = "1,2,3,4", required = true)
                                                         @PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/photo")
    @ApiOperation(notes = "Upload photo of the employee by id", value = "Upload photo employee by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Response ok if the operation was successful"),
                            @ApiResponse(code = 400, message = "Response bad request if the resource is not correct")})
    public ResponseEntity<EmployeeDTO> uploadPhotoById(@ApiParam(example = "1L", value = "Identifier for the user",
                                                          allowableValues = "1,2,3,4", required = true)
                                                          @PathVariable Long id, @RequestParam("photo") MultipartFile file) {
        try {
            EmployeeDTO employeeDTO = employeeService.uploadPhotoEmployee(id, file);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/photo")
    @ApiOperation(notes = "Retrieve the photo's name of the employee by id", value = "Get photo name employee by id")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Response no content if the operation was successful"),
                            @ApiResponse(code = 200, message = "Response ok if the operation was successful"),
                            @ApiResponse(code = 400, message = "Response bad request if the resource is not correct")})
    public ResponseEntity<String> getPhotoNameById(@PathVariable Long id) {
        try {
            String photoNameEmployee = employeeService.getPhotoNameEmployee(id);
            if (photoNameEmployee == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(photoNameEmployee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
