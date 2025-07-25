package com.company.employeeapp.service;

import com.company.employeeapp.feignClient.AddressClient;
import com.company.employeeapp.model.Employee;
import com.company.employeeapp.repository.EmployeeRepository;
import com.company.employeeapp.response.AddressResponse;
import com.company.employeeapp.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressClient addressClient;

    public ResponseEntity<?> getEmployeeById(Integer empId){

        //addressResponse -> set data by making a rest api call

        Optional<Employee> optional = employeeRepository.findById(empId);
        if(optional.isPresent()){
            //Employee --> EmployeeResponse
            EmployeeResponse employeeResponse = modelMapper.map(optional.get(), EmployeeResponse.class);

            //using restTemplate thread will not be asynchronous
//            AddressResponse addressResponse = callingAddressServiceUsingRestTemplate(empId);

            //using FeignClient
            ResponseEntity<AddressResponse> addressEntityResponse = addressClient.getAddressByEmpId(empId);
            AddressResponse addressResponse = addressEntityResponse.getBody();

            employeeResponse.setAddressResponse(addressResponse);

            return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
        }
        return new ResponseEntity<String>("Data not found", HttpStatus.NOT_FOUND);
    }

}
