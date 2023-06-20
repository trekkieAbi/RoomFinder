package com.room.finder.mapper;

import com.room.finder.model.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    Integer saveCustomer(Customer customer);
    Integer updateCustomer(Customer customer);
    Customer findCustomerById(Integer id);
    Customer findCustomerByUser(Integer userId);
}
