package com.craftsmen.graphite.monitoring.example.controller.graphite;

import static com.craftsmen.graphite.monitoring.example.controller.AbstractHttpController.CUSTOMER_CONTROLLER_PATH;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.craftsmen.graphite.monitoring.example.controller.AbstractHttpController;
import com.craftsmen.graphite.monitoring.example.entities.Customer;
import com.craftsmen.graphite.monitoring.example.service.CustomerService;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;

@RestController
@RequestMapping(CUSTOMER_CONTROLLER_PATH)
@EnableMetrics
public class CustomerController extends AbstractHttpController {

	@Inject
	private CustomerService customerService;

	@Inject
	private ResponseBodyMapper responseBodyMapper;

	/**
	 * This method will retrieve the {@link Customer} for the id provided.
	 *
	 * If the {@link Customer} cannot be found, a {@link NoSuchElementException}
	 * will be thrown and a {@link ResponseEntity} with
	 * {@link HttpStatus.NO_CONTENT} will be returned. If the {@link Customer}
	 * exists, it will be returned in the {@link ResponseEntity}.
	 *
	 * @param id
	 *            the unique identifier for this customer
	 * @return {@link ResponseEntity} containing {@link Customer} retrieved if
	 *         found, the HTTP headers and the HTTP status code
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> get(@PathVariable Long id, HttpServletRequest request) {
		logApiCalls(request);
		String requestUrl = linkTo(CustomerController.class).withSelfRel().getHref();
		Customer customer = customerService.findById(id);
		String response = responseBodyMapper.mapCustomerToResonseBody(customer, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}
}
