package com.craftsmen.graphite.monitoring.example.service;

import java.util.List;

import com.craftsmen.graphite.monitoring.example.entities.Customer;

public interface CustomerService {

	Customer findById(Long id);

	List<Customer> findByFirstName(String firstName);

	List<Customer> findByLastName(String lastName);

	void delete(Long id);

	Customer save(Customer customer);
}
