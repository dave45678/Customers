package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    ArrayList<Customer> findAllByLastName(String name);
    /*This is essentially running in SQL:
        SELECT * FROM Customer
        WHERE lastName = "<searchTerm>";
    */
}
