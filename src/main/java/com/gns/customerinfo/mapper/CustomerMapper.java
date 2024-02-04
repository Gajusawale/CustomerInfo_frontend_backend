package com.gns.customerinfo.mapper;

import com.gns.customerinfo.dto.CustomerDTO;
import com.gns.customerinfo.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE= Mappers.getMapper(CustomerMapper.class);

    Customer mapCustomerDTOToCustomer(CustomerDTO customerDTO);

    CustomerDTO mapCustomerToCustomerDTO(Customer customer);
}
