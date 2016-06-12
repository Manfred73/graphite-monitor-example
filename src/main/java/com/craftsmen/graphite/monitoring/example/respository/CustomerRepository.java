package com.craftsmen.graphite.monitoring.example.respository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.craftsmen.graphite.monitoring.example.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByFirstName(String firstName);
	
    List<Customer> findByLastName(String lastName);
    
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
