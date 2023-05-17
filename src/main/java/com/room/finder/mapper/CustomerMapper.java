package com.room.finder.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.room.finder.model.Customer;

@Mapper
public interface CustomerMapper {
    Integer saveCustomer(Customer customer);
    Integer updateCustomer(Customer customer);
    Customer findCustomerById(Integer id);
    Customer findCustomerByUser(Integer userId);
}

