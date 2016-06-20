package com.craftsmen.graphite.monitoring.example.controller.customer;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import com.craftsmen.graphite.monitoring.example.entities.Customer;
import com.craftsmen.graphite.monitoring.example.util.JsonUtil;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Named("customerToJsonMapper")
@Singleton
public class CustomerToJsonMapper {

	public String mapCustomerToJson(Customer customer, String requestUrl) {
		return constructJsonCustomer(requestUrl, customer).toString();
	}

	public String mapCustomerToJsonWithoutId(Customer customer, String requestUrl) {
		return constructJsonCustomerWithoutId(requestUrl, customer).toString();
	}

	public String mapCustomersToJson(List<Customer> customers, String requestUrl) {
		JSONObject json = new JSONObject();
		JSONArray jsonCustomers = new JSONArray();
		for (Customer customer : customers) {
			jsonCustomers.add(constructJsonCustomer(requestUrl, customer));
		}
		json.put("customers", jsonCustomers);
		return json.toString();
	}

	private JSONObject constructJsonCustomerWithoutId(String requestUrl, Customer customer) {
		JSONObject json = new JSONObject();
		json.put("links", JsonUtil.createSelfUrl(requestUrl, customer.getId()));
		json.put("firstName", customer.getFirstName());
		json.put("lastName", customer.getLastName());
		return json;
	}

	private JSONObject constructJsonCustomer(String requestUrl, Customer customer) {
		JSONObject json = constructJsonCustomerWithoutId(requestUrl, customer);
		json.put("id", customer.getId());
		return json;
	}
}
