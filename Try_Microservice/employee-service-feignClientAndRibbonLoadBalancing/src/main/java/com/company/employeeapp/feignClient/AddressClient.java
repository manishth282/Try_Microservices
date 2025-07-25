package com.company.employeeapp.feignClient;

import com.company.employeeapp.response.AddressResponse;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-service11", path = "/address-app/api/")
@RibbonClient(name = "address-service1")
public interface AddressClient {

    @GetMapping("/address/{empId}")
    public ResponseEntity<AddressResponse> getAddressByEmpId(@PathVariable("empId") int empId);
}
