package com.craftsmen.graphite.monitoring.example.controller.graphite;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import com.craftsmen.graphite.monitoring.example.entities.Customer;
import com.craftsmen.graphite.monitoring.example.util.JsonUtil;

@Named("customerResponseBodyMapper")
@Singleton
public class ResponseBodyMapper {

	public String mapCustomerToResonseBody(Customer customer, String requestUrl) {
		return constructJsonCustomer(requestUrl, customer);
	}

	public String mapCustomersToResponseBody(List<Customer> customers, String requestUrl) {
		JSONObject json = new JSONObject();
		JSONArray jsonCustomers = new JSONArray();
		for (Customer customer : customers) {
			jsonCustomers.put(constructJsonCustomer(requestUrl, customer));
		}
		json.put("customers", jsonCustomers);
		return json.toString();
	}

	private String constructJsonCustomer(String requestUrl, Customer customer) {
		JSONObject json = new JSONObject();
		json.put("_links", JsonUtil.createSelfUrl(requestUrl, customer.getId()))
				.put("firstName", customer.getFirstName()).put("lastName", customer.getLastName());
		return json.toString();
	}
}
