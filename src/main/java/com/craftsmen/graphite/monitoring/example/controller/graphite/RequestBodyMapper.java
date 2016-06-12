package com.craftsmen.graphite.monitoring.example.controller.graphite;

import javax.inject.Named;
import javax.inject.Singleton;

import com.craftsmen.graphite.monitoring.example.entities.Customer;
import com.craftsmen.graphite.monitoring.example.util.JsonUtil;
import com.jayway.jsonpath.DocumentContext;

@Named("customerRequestBodyMapper")
@Singleton
public class RequestBodyMapper {

	public Customer mapRequestBodyToCustomer(String requestBody) {
		return mapRequestBodyToCustomer(null, requestBody);
	}

	public Customer mapRequestBodyToCustomer(Long id, String requestBody) {
		DocumentContext jsonContext = JsonUtil.getJsonContext(requestBody);
		String firstName = (String) jsonContext.read("$.firstName");
		String lastName = (String) jsonContext.read("$.lastName");
		if (id == null) {
			return new Customer(firstName, lastName);
		}
		return new Customer(id, firstName, lastName);
	}
}
