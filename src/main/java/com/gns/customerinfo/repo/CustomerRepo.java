package com.gns.customerinfo.repo;

import com.gns.customerinfo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Long> {


    Optional<Customer> findByFirstName(String firstName);

}
