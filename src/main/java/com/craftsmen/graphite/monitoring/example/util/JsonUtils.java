package com.craftsmen.graphite.monitoring.example.util;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

public class JsonUtils {

	private JsonUtils() {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}

	public static Configuration getJsonConfiguration() {
		return Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL,
				Option.SUPPRESS_EXCEPTIONS);
	}

	public static DocumentContext getJsonContext(String requestBody) {
		return JsonPath.using(getJsonConfiguration()).parse(requestBody);
	}
}
