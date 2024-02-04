package com.gns.customerinfo.service;

import com.gns.customerinfo.dto.CustomerDTO;
import com.gns.customerinfo.dto.CustomerDTOResponse;
import com.gns.customerinfo.entity.Customer;
import com.gns.customerinfo.exception.ResourceNotFoundException;
import com.gns.customerinfo.mapper.CustomerMapper;
import com.gns.customerinfo.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;
    public CustomerDTO addUser(CustomerDTO customerDTO) {
        Customer addeCustomer=customerRepo.save(CustomerMapper.INSTANCE.mapCustomerDTOToCustomer(customerDTO));
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(addeCustomer);
    }

    public ResponseEntity<CustomerDTO> getCustomerById(Long customerId) {
        Optional<Customer> customer=customerRepo.findById(customerId);

        return customer.map(value -> new ResponseEntity<>
                (CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {

        Customer customerGetById=customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer","id",id));

        customerGetById.setFirstName(customerDTO.getFirstName());
        customerGetById.setLastName(customerDTO.getLastName());
        customerGetById.setAddress(customerDTO.getAddress());
        customerGetById.setStreet(customerDTO.getStreet());
        customerGetById.setCity(customerGetById.getCity());
        customerGetById.setState(customerDTO.getState());
        customerGetById.setEmail(customerDTO.getEmail());
        customerGetById.setPhone(customerDTO.getPhone());

        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customerGetById);
    }

    public void deleteCustomerByName(String firstName) {

       Customer customer= customerRepo.findByFirstName(firstName).orElseThrow(()->new ResourceNotFoundException("Resource Not found with this name"));

        customerRepo.delete(customer);
    }

    public void deleteCustomerById(Long id) {

        Customer customer= customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("customer","id",id));

        customerRepo.delete(customer);
    }

    public CustomerDTOResponse getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);

        Page<Customer> customers= customerRepo.findAll(pageable);

        List<Customer> customerList=customers.getContent();

        List<CustomerDTO> customerDTOS=customerList.stream().
                map(this::mapToDTO).toList();

        CustomerDTOResponse customerDTOResponse=new CustomerDTOResponse();

        customerDTOResponse.setContent(customerDTOS);
        customerDTOResponse.setPageNo(customers.getNumber());
        customerDTOResponse.setPageSize(customers.getSize());
        customerDTOResponse.setTotalElements(customers.getTotalElements());
        customerDTOResponse.setTotalPages(customers.getTotalPages());
        customerDTOResponse.setLast(customers.isLast());

        return  customerDTOResponse;

    }

    private CustomerDTO mapToDTO(Customer customer)
    {
        return CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customer);
    }

}
