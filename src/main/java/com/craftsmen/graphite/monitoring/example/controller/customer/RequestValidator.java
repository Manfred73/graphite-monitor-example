package com.craftsmen.graphite.monitoring.example.controller.customer;

import javax.inject.Singleton;

import org.springframework.stereotype.Component;

import com.craftsmen.graphite.monitoring.example.util.JsonUtil;
import com.jayway.jsonpath.DocumentContext;

@Component
@Singleton
public class RequestValidator {

	public void validateJson(String json) {
		DocumentContext jsonContext;
		try {
			jsonContext = JsonUtil.getJsonContext(json);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("Error in JSON format");
		}
		if (jsonContext.read("$.id") != null) {
			System.out.println("Customer shouldn't contain id");
			throw new IllegalArgumentException("Customer shouldn't contain id");
		}
	}
}
