package com.company.addressapp.service;

import com.company.addressapp.model.Address;
import com.company.addressapp.repository.AddressRepo;
import com.company.addressapp.response.AddressResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    ModelMapper modelMapper;
    public ResponseEntity<?> getAddressByEmpId(int empId){
        Address address = addressRepo.getAddressByEmpId(empId);
        //Validation for addresResponse has data or null
        if(address == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found according to emp Id");

        AddressResponse addressResponse = modelMapper.map(address, AddressResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(addressResponse);
    }
}
