package com.company.addressapp.controller;

import com.company.addressapp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/addressapp")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/address/{empId}")
    public ResponseEntity<?> getAddressByEmpId(@PathVariable("empId") int empId){

        return addressService.getAddressByEmpId(empId);
    }
}
