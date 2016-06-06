package com.craftsmen.graphite.monitoring.example.controller.graphite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import com.craftsmen.graphite.monitoring.example.entities.Customer;

@Named("customerResponseBodyMapper")
@Singleton
public class ResponseBodyMapper {

	public String mapCustomerToResonseBody(Customer customer, String requestUrl) {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		return mapCustomersToResponseBody(customers, requestUrl);
	}
	
	public String mapCustomersToResponseBody(List<Customer> customers, String requestUrl) {
		JSONObject json = new JSONObject();
		JSONArray jsonCustomers = new JSONArray();
		for (Customer customer : customers) {
			String selfUrl = new StringBuilder().append(requestUrl).append("/").append(customer.getId()).toString();
			json.put("selfUrl", selfUrl).put("firstName", customer.getFirstName()).put("lastName", customer.getLastName());
		}
		json.put("customers", jsonCustomers);
		return json.toString();
	}
}
