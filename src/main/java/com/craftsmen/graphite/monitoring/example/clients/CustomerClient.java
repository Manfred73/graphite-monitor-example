package com.craftsmen.graphite.monitoring.example.clients;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.craftsmen.graphite.monitoring.example.controller.customer.CustomerToJsonMapper;
import com.craftsmen.graphite.monitoring.example.controller.customer.JsonToCustomerMapper;
import com.craftsmen.graphite.monitoring.example.entities.Customer;
import com.craftsmen.graphite.monitoring.example.util.EndPointMatcher;

public class CustomerClient {

	private static final String CUSTOMER_ENDPOINT_BY_ID = "/customer/{id}";
	private static final String CUSTOMER_ENDPOINT = "/customer";
	private static final String CUSTOMERS_ENDPOINT_GET_BY_FIRST_NAME = "/customers/firstName/{firstName}";
	private static final String CUSTOMERS_ENDPOINT_GET_BY_LAST_NAME = "/customers/lastName/{lastName}";
	private static final String CUSTOMERS_ENDPOINT_GET_BY_FIRST_NAME_AND_LAST_NAME = "/customers/firstName/{firstName}/lastName/{lastName}";
	private static final String CUSTOMERS_ENDPOINT_GET_ALL = "/customers";

	@Value("${server.port}")
	private int serverPort;

	private String baseUrl;

	@Inject
	private EndPointMatcher endPointMatcher;

	@Inject
	private RestTemplate restTemplate;

	@Inject
	private JsonToCustomerMapper jsonToCustomerMapper;

	@Inject
	private CustomerToJsonMapper customerToJsonMapper;

	public Customer getCustomerById(Long id) {
		return getCustomer(
				endPointMatcher.constructEndPoint(CUSTOMER_ENDPOINT_BY_ID, new String[] { String.valueOf(id) }));
	}

	public List<Customer> getCustomerByFirstName(String firstName) {
		return getCustomers(
				endPointMatcher.constructEndPoint(CUSTOMERS_ENDPOINT_GET_BY_FIRST_NAME, new String[] { firstName }));
	}

	public List<Customer> getCustomerByLastName(String lastName) {
		return getCustomers(
				endPointMatcher.constructEndPoint(CUSTOMERS_ENDPOINT_GET_BY_LAST_NAME, new String[] { lastName }));
	}

	public List<Customer> getCustomerByFirstNameAndLastName(String firstName, String lastName) {
		return getCustomers(endPointMatcher.constructEndPoint(CUSTOMERS_ENDPOINT_GET_BY_FIRST_NAME_AND_LAST_NAME,
				new String[] { firstName, lastName }));
	}

	public List<Customer> getAllCustomers() {
		return getCustomers(endPointMatcher.constructEndPoint(CUSTOMERS_ENDPOINT_GET_ALL, new String[] {}));
	}

	public Customer createCustomer(Customer customer) {
		return postCustomer(CUSTOMER_ENDPOINT, customer);
	}

	public Customer updateCustomer(Customer customer) {
		return putCustomer(endPointMatcher.constructEndPoint(CUSTOMER_ENDPOINT_BY_ID,
				new String[] { String.valueOf(customer.getId()) }), customer);
	}

	public void deleteCustomer(Long id) {
		deleteCustomer(endPointMatcher.constructEndPoint(CUSTOMER_ENDPOINT_BY_ID, new String[] { String.valueOf(id) }));
	}

	private Customer getCustomer(String endPoint) {
		try {
			ResponseEntity<String> response = restTemplate.exchange(baseUrl + endPoint, HttpMethod.GET, getEntity(),
					String.class);
			return jsonToCustomerMapper.mapJsonToCustomer(response.getBody());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<Customer> getCustomers(String endPoint) {
		try {
			ResponseEntity<String> response = restTemplate.exchange(baseUrl + endPoint, HttpMethod.GET, getEntity(),
					String.class);
			return jsonToCustomerMapper.mapJsonToCustomers(response.getBody());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Customer postCustomer(String endPoint, Customer customer) {
		try {
			ResponseEntity<String> response = restTemplate.exchange(baseUrl + endPoint, HttpMethod.POST,
					getEntityWithPayload(customer), String.class);
			return jsonToCustomerMapper.mapJsonToCustomer(response.getBody());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Customer putCustomer(String endPoint, Customer customer) {
		try {
			ResponseEntity<String> response = restTemplate.exchange(baseUrl + endPoint, HttpMethod.PUT,
					getEntityWithPayload(customer), String.class);
			return jsonToCustomerMapper.mapJsonToCustomer(response.getBody());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void deleteCustomer(String endPoint) {
		try {
			restTemplate.exchange(baseUrl + endPoint, HttpMethod.DELETE, getEntity(), String.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private HttpEntity<String> getEntity() {
		return new HttpEntity<String>(createHttpHeaders());
	}

	private HttpEntity<String> getEntityWithPayload(Customer customer) {
		return new HttpEntity<String>(customerToJsonMapper.mapCustomerToJsonWithoutId(customer, baseUrl),
				createHttpHeaders());
	}

	private HttpHeaders createHttpHeaders() {
		List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(mediaTypes);
		return headers;
	}

	@PostConstruct
	public void init() {
		try {
			baseUrl = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort;
		} catch (UnknownHostException e) {
			throw new RuntimeException("Error looking up localhost in CustomerClient.init");
		}
	}
}
