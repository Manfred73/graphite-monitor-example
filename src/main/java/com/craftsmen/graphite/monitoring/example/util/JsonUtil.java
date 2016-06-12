package com.craftsmen.graphite.monitoring.example.util;

import org.json.JSONObject;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

public class JsonUtil {

	private JsonUtil() {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}

	public static Configuration getJsonConfiguration() {
		return Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL,
				Option.SUPPRESS_EXCEPTIONS);
	}

	public static DocumentContext getJsonContext(String requestBody) {
		return JsonPath.using(getJsonConfiguration()).parse(requestBody);
	}
	
	public static String createSelfUrl(String requestUrl, Long id) {
		JSONObject json = new JSONObject();
		String selfUrl = new StringBuilder().append(requestUrl).append("/").append(id).toString();
		json.put("self", new JSONObject().put("href", selfUrl));
		return json.toString();
	}
}
