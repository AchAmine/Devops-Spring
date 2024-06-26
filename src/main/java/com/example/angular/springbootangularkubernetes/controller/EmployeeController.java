package com.example.angular.springbootangularkubernetes.controller;

import com.example.angular.springbootangularkubernetes.ResourceNotFoundException;
import com.example.angular.springbootangularkubernetes.ForbiddenException;
import com.example.angular.springbootangularkubernetes.TokenException;
import com.example.angular.springbootangularkubernetes.model.Employee;
import com.example.angular.springbootangularkubernetes.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    
 //   @Value("${spring.datasource.password}")
   // private String pass;

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }


    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
  //      log.info("DATABASE PASSWORD : "+this.pass);
        return ResponseEntity.ok(employeeRepository.findAll());
    }
    
/*    @GetMapping("/employees/test")
    public String getAllEmployees2() {
        log.info("DATABASE PASSWORD : "+this.pass);
        return this.pass;
    }
    
    @GetMapping("/employees/test3")
    public String getAllEmployees3() {
        log.info("DATABASE PASSWORD2 : "+pass);
        return pass;
    }*/

   @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Integer employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer employeeId,
                                                   @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setDepartment(employeeDetails.getDepartment());

        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Integer employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    } 



    @GetMapping("/")
    public String HomePage(){
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("Welcome home Page " + localDateTime);
        return "Welcome to Home page";
    }

    @GetMapping("/logs")
    public String LogsPage(){
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("This Logs page " + localDateTime);
        return "Welcome to logs page";
    }

    @GetMapping("/warn")
    public String WarnPage(){
        LocalDateTime localDateTime = LocalDateTime.now();
        log.warn("This warn page " + localDateTime);
        return "Welcome to warn page";
    }

    @GetMapping("/forb/{id}")
    public ResponseEntity<String> forbPage(@PathVariable(value = "id") Integer id) throws ForbiddenException {
   //     try {
            if (id == 2) {
                throw new ForbiddenException("forbidden exception");
            }
          return new ResponseEntity<>("Success", HttpStatus.OK);
  /*      } catch (ForbiddenException e) {
            log.error("Forbidden access error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error("An error occurred: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } */
    }


    @GetMapping("/forb/emp/{id}")
    public ResponseEntity<Employee> forbEmployeeId(@PathVariable(value = "id") Integer employeeId)
            throws ForbiddenException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ForbiddenException("Employee not found for this id"));
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/exception/test")
    public void testException() throws TokenException {
        try {
            int result = 10 / 0;
            log.info("Result: " + result);
        } catch (ArithmeticException cause) {
            throw new TokenException("Error performing a calculation", cause);
        }
    }

}

