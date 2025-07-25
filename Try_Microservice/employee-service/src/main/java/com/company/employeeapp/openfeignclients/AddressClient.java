package com.company.employeeapp.openfeignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ADDRESS-SERVICE", path = "/address-app/api")
public interface AddressClient {
    @GetMapping("/address/{empId}")
    public ResponseEntity<?> getAddressByEmpId(@PathVariable("empId") int empId);
}
