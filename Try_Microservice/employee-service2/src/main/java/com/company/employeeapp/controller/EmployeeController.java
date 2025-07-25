package com.company.employeeapp.controller;

import com.company.employeeapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/employeeapp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/{empId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("empId") Integer empId){

        return employeeService.getEmployeeById(empId);
    }
}
