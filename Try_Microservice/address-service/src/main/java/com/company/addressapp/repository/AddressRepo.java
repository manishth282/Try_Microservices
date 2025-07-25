package com.company.addressapp.repository;

import com.company.addressapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepo extends JpaRepository<Address, Integer> {

    @Query(value = "select ad.id, ad.lane1, ad.lane2, ad.state, ad.zip " +
            "from address ad " +
            "join employee em on em.id=ad.employee_id " +
            "where em.id =:empId", nativeQuery = true)
    Address getAddressByEmpId(@Param("empId") int empId);
}
