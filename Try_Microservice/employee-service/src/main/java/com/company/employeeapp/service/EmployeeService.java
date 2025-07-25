package com.company.employeeapp.service;

import com.company.employeeapp.model.Employee;
import com.company.employeeapp.openfeignclients.AddressClient;
import com.company.employeeapp.repository.EmployeeRepository;
import com.company.employeeapp.response.AddressResponse;
import com.company.employeeapp.response.EmployeeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private WebClient webClient;

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private AddressClient addressClient;

    @Autowired
    private ObjectMapper objectMapper; //use to convert Object type to AddressResponse

//    @Value("${addressservice.base.url}")
//    private String addressBaseURL;

//    public EmployeeService(@Value("${addressservice.base.url}")String addressBaseURL, RestTemplateBuilder builder){
//        this.restTemplate = builder
//                            .rootUri(addressBaseURL)
//                            .build();
//    }

    public ResponseEntity<?> getEmployeeById(Integer empId){

        //addressResponse -> set data by making a rest api call

        Optional<Employee> optional = employeeRepository.findById(empId);
        if(optional.isPresent()){
            //Employee --> EmployeeResponse
            EmployeeResponse employeeResponse = modelMapper.map(optional.get(), EmployeeResponse.class);

            //using restTemplate thread will not be asynchronous
//            AddressResponse addressResponse = callingAddressServiceUsingRestTemplate(empId);
//            employeeResponse.setAddressResponse(addressResponse);

            //using webClient thread will work as asynchronous
//            AddressResponse addressResponse = callToAddressServiceUsingWebClient(empId);
//            employeeResponse.setAddressResponse(addressResponse);

            //using openfeign client
            ResponseEntity<?> responseEntity = addressClient.getAddressByEmpId(empId);
            Object body = responseEntity.getBody();
            AddressResponse addressResponse = objectMapper.convertValue(body, AddressResponse.class);
            employeeResponse.setAddressResponse(addressResponse);

            return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
        }
        return new ResponseEntity<String>("Data not found", HttpStatus.NOT_FOUND);
    }

    private AddressResponse callingAddressServiceUsingRestTemplate(Integer empId) {
        //1>
//        List<ServiceInstance> instances = discoveryClient.getInstances("address-service");
//        String uri = instances.get(0).getUri().toString();
//        return restTemplate.getForObject(uri+"/address-app/api/address/{id}", AddressResponse.class, empId);

        //2.1> Using LoadBalancerClient which takes registry details from cache which it got from eureka service. (client side loadbalancer)
//        ServiceInstance serviceInstance = loadBalancerClient.choose("address-service");
//        String uri = serviceInstance.getUri().toString();
//        String contextPath = serviceInstance.getMetadata().get("configPath");
//        System.out.println(("uri ::::: : "+uri+contextPath));
//        return restTemplate.getForObject(uri+contextPath+"/address/{id}", AddressResponse.class, empId);

        //2.2> Using @LoadBalanced in Configuration class while creating Bean of RestTemplate. (client side loadbalancer)
        return restTemplate.getForObject("http://address-service/address-app/api/address/{id}", AddressResponse.class, empId);


    }

//    private  AddressResponse callToAddressServiceUsingWebClient(Integer empId){
//
//        AddressResponse addressResponse = webClient
//                .get()
//                .uri("/address/"+empId)
//                .retrieve()
//                .bodyToMono(AddressResponse.class)
//                .block();
//        return addressResponse;
//    }
}
