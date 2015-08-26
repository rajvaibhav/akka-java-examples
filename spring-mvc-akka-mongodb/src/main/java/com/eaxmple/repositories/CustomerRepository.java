package com.eaxmple.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eaxmple.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, String> {

}
