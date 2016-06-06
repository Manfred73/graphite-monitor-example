package com.craftsmen.graphite.monitoring.example.controller;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class AbstractHttpController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpController.class);
	public static final String JSON_MIME_TYPE = "application/json";
	public static final String CUSTOMER_CONTROLLER_PATH = "/customer";
	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";
	public static final String HTTP_PUT = "PUT";
	public static final String HTTP_DELETE = "DELETE";

	private MultiValueMap<String, String> createHeaders() {
		return createHeaders(null, null);
	}

	protected MultiValueMap<String, String> createHeaders(String requestMethod) {
		return createHeaders(null, requestMethod);
	}

	protected MultiValueMap<String, String> createHeaders(String locationUrl, String requestMethod) {
		MultiValueMap<String, String> headers = new HttpHeaders();
		if (locationUrl != null && HTTP_POST.equals(requestMethod)) {
			headers.add(HttpHeaders.LOCATION, locationUrl);
		}
		headers.add(HttpHeaders.CONTENT_TYPE, JSON_MIME_TYPE);
		return headers;
	}

	@ExceptionHandler({ IllegalArgumentException.class, ClassCastException.class })
	public ResponseEntity<String> badRequest(Exception e) {
		return new ResponseEntity<>(e.getMessage(), createHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> forbiddenRequest(Exception e) {
		LOGGER.warn(e.getMessage());
		return new ResponseEntity<>("", createHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> internalServerError(Exception e) {
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<>("", createHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> notFound(Exception e) {
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<>(e.getMessage(), createHeaders(), HttpStatus.NOT_FOUND);
	}

	protected void logApiCalls(HttpServletRequest request) {
		LOGGER.info(getSelfUrl(request) + " - " + request.getMethod());
	}

	protected String getSelfUrl(HttpServletRequest request) {
		return request.getRequestURL().toString()
				+ (request.getQueryString() == null || request.getQueryString().isEmpty() ? ""
						: "?" + (request.getQueryString()));
	}
}
