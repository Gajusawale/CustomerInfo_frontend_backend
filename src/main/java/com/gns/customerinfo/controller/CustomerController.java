package com.gns.customerinfo.controller;

import com.gns.customerinfo.dto.CustomerDTO;
import com.gns.customerinfo.dto.CustomerDTOResponse;
import com.gns.customerinfo.service.CustomerService;
import com.gns.customerinfo.utility.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Rest controller for handle the crud operations which is coming from the frontend
@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;




    // build rest api for Add Customer
    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerDTO> addUser(@RequestBody CustomerDTO customerDTO)
    {

       CustomerDTO addedCustomer= customerService.addUser(customerDTO);

       return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    // build rest api for the fetch all customers
        @GetMapping("/getAllCustomer")
    public CustomerDTOResponse getAllCustomer(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

    )
    {
        return customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);
    }

    // build rest api for the fetch customer by id
    @GetMapping("/getCustomerById/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId)
    {
        return customerService.getCustomerById(customerId);

    }

//    build rest api for the edit exiting customerf
    @PutMapping("/editCustomer/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO,
                                                      @PathVariable Long id)
    {
        CustomerDTO editCustomerDTO= customerService.updateCustomer(id,customerDTO);

        return new ResponseEntity<>(editCustomerDTO,HttpStatus.OK);
    }

    //build rest api for the delete customer by firstName
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteCustomerName/{firstName}")
    public ResponseEntity<String> deleteCustomerByName(@PathVariable String firstName)
    {
        customerService.deleteCustomerByName(firstName);

        return new ResponseEntity<>("customer deleted successfully",HttpStatus.OK);
    }

//    build rest api for the delete customer by Id
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id)
    {
        customerService.deleteCustomerById(id);

        return new ResponseEntity<>("customer deleted successfully",HttpStatus.OK);
    }






}
