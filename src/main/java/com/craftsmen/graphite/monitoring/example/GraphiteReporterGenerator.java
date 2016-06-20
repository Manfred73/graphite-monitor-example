package com.craftsmen.graphite.monitoring.example;

import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.craftsmen.graphite.monitoring.example.clients.CustomerClient;
import com.craftsmen.graphite.monitoring.example.entities.Customer;

@Component
public class GraphiteReporterGenerator implements ApplicationRunner {

	private static final String OPTION_GENERATE_GRAPHITE_DATA = "generateGraphiteData";

	@Inject
	private CustomerClient customerClient;

	@Override
	public void run(ApplicationArguments arguments) throws Exception {
		if (arguments.containsOption(OPTION_GENERATE_GRAPHITE_DATA)) {
			for (int i = 0; i < 1000; i++) {

				Long id = new Long((int) Math.ceil((i / 3.35) + 1));

				System.out.println("***** GetCustomerById *****");
				Customer customer = customerClient.getCustomerById(id);
				printCustomer(customer);

				System.out.println("***** GetCustomerByFirstName *****");
				List<Customer> customers = customerClient.getCustomerByFirstName("Addison");
				printCustomers(customers);

				System.out.println("***** GetCustomerByLastName *****");
				customers = customerClient.getCustomerByLastName("Alvin");
				printCustomers(customers);

				System.out.println("***** GetCustomerByFirstNameAndLastName *****");
				customers = customerClient.getCustomerByFirstNameAndLastName("Carlos", "Lucius");
				printCustomers(customers);

				System.out.println("***** GetAllCustomers *****");
				customers = customerClient.getAllCustomers();
				System.out.println(">>> Total number of customers: " + customers.size());

				System.out.println("***** CreateCustomer *****");
				customer = customerClient.createCustomer(new Customer("John", "Doe"));
				printCustomer(customer);

				System.out.println("***** UpdateCustomer *****");
				customer = customerClient
						.updateCustomer(new Customer(customer.getId(), "Jane", customer.getLastName()));
				printCustomer(customer);

				System.out.println("***** DeleteCustomer *****");
				customerClient.deleteCustomer(customer.getId());

				System.out.println("***** GetCustomerByFirstNameAndLastName *****");
				customers = customerClient.getCustomerByFirstNameAndLastName("Jane", "Doe");
				printCustomers(customers);
			}
		}
	}

	private void printCustomer(Customer customer) {
		System.out.println(customer.toString());
	}

	private void printCustomers(List<Customer> customers) {
		System.out.println(">>> Total number of customers: " + customers.size());
		for (Customer customer : customers) {
			System.out.println(customer.toString());
		}
	}
}
