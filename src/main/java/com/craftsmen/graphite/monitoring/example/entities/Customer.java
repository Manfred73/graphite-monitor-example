package com.craftsmen.graphite.monitoring.example.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Customer {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_ID_SEQ")
    @SequenceGenerator(name="CUSTOMER_ID_SEQ", sequenceName = "CUSTOMER_ID_SEQ", allocationSize = 1)
	private Long id;
	private String firstName;
	private String lastName;

	protected Customer() {
	}

	public Customer(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
	}
}
