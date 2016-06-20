package com.craftsmen.graphite.monitoring.example.controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import com.craftsmen.graphite.monitoring.example.entities.Customer;
import com.craftsmen.graphite.monitoring.example.util.JsonUtil;
import com.jayway.jsonpath.DocumentContext;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;


@Named("jsonToCustomerMapper")
@Singleton
public class JsonToCustomerMapper {

	public Customer mapJsonToCustomer(String json) {
		return mapJsonToCustomer(null, json);
	}

	public List<Customer> mapJsonToCustomers(String json) throws ParseException {
		List<Customer> customers = new ArrayList<>();
		DocumentContext jsonContext = JsonUtil.getJsonContext(json);
		JSONArray jsonCustomers = jsonContext.read("$.customers");
		for (int i = 0; i < jsonCustomers.size(); i++) {
		    //JSONObject jsonObject = (JSONObject) new JSONParser(JSONParser.MODE_JSON_SIMPLE).parse((String) jsonContext.read("$.customers[" + i + "]"));
			Number id = jsonContext.read("$.customers[" + i + "].id");
			String firstName = jsonContext.read("$.customers[" + i + "].firstName");
			String lastName =  jsonContext.read("$.customers[" + i + "].lastName");
			customers.add(new Customer(id.longValue(), firstName, lastName));
		}
		return customers;
	}
	
	public Customer mapJsonToCustomer(Long id, String json) {
		DocumentContext jsonContext = JsonUtil.getJsonContext(json);
		Number jsonId = jsonContext.read("$.id");
		String firstName = jsonContext.read("$.firstName");
		String lastName = jsonContext.read("$.lastName");
		if (jsonId != null) {
			return new Customer(jsonId.longValue(), firstName, lastName);
		}
		if (id == null) {
			return new Customer(firstName, lastName);
		}
		return new Customer(id, firstName, lastName);
	}
}
