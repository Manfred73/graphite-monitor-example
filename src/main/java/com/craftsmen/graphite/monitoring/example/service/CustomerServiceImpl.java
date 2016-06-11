package com.craftsmen.graphite.monitoring.example.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.craftsmen.graphite.monitoring.example.entities.Customer;
import com.craftsmen.graphite.monitoring.example.respository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Inject
	private CustomerRepository repository;

	public Customer findById(Long id) {
		Customer customer = repository.findOne(id);
		if (null == customer) {
			throw new NoSuchElementException("Customer with id " + id + " not found");
		}
		return customer;
	}

	public List<Customer> findByLastName(String lastName) {
		List<Customer> customers = repository.findByLastName(lastName);
		return customers;
	}

	public List<Customer> findByFirstName(String firstName) {
		List<Customer> customers = repository.findByFirstName(firstName);
		return customers;
	}

	public void delete(Long id) {
		Customer customer = repository.findOne(id);
		if (customer != null) {
			repository.delete(id);
		}
	}

	public Customer save(Customer customer) {
		return repository.save(customer);
	}
}
