package com.craftsmen.graphite.monitoring.example.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

@Component
public class EndPointMatcher {

	private static final String regex = "\\{[^{{}]*\\}";
	private static final Pattern pattern = Pattern.compile(regex);

	/**
	 * This method constructs the proper REST endpoint by replacing all
	 * occurrences of {...} in the endpoint with the arguments provided. The
	 * number of occurrences of {...} in the endpoint need to be exactly the
	 * same as the number of arguments provided. If this is not the case a
	 * {@link RuntimeException} will be thrown.
	 * 
	 * The arguments are replaced within the endpoint in the order they are
	 * provided. E.g. if the endpoint is
	 * <code>/customers/firstName/{firstName}/lastName/{lastName}</code> and the
	 * arguments { "John", "Doe" }, the resulting endpoint will be
	 * <code><code>/customers/firstName/John/lastName/Doe</code>
	 * 
	 * @param endPoint
	 *            the endpoint containing {...} placeholders which need to be
	 *            replaced
	 * @param arguments
	 *            the arguments used to replace the {...} placeholders in the
	 *            endpoint with
	 * @return the new endpoint where {...} placeholders have been replaced
	 */
	public String constructEndPoint(String endPoint, String... arguments) {
		validateNumberOfArgumentsWithNumberOfPlaceHoldersInEndPoint(arguments, endPoint);
		Matcher matcher = pattern.matcher(endPoint);
		int numberOfMatchingArgumentsFound = 0;
		while (matcher.find()) {
			endPoint = matcher.replaceFirst(arguments[numberOfMatchingArgumentsFound++]);
			matcher = pattern.matcher(endPoint);
		}
		return endPoint;
	}

	private void validateNumberOfArgumentsWithNumberOfPlaceHoldersInEndPoint(String[] arguments, String endPoint) {
		int numberOfPlaceHoldersInEndPoint = findNumberOfPlaceHoldersInEndPoint(endPoint);
		if (arguments.length < numberOfPlaceHoldersInEndPoint) {
			throw new RuntimeException("Error in matching number of arguments " + ArrayUtils.toString(arguments)
					+ " with endpoint " + endPoint
					+ ". The number of arguments is less than the number of placeholders that need to be replaced in the endpoint.");
		}
		if (arguments.length > numberOfPlaceHoldersInEndPoint) {
			throw new RuntimeException("Error in matching number of arguments " + ArrayUtils.toString(arguments)
					+ " with endpoint " + endPoint
					+ ". The number of arguments is greater than the number of placeholders that need to be replaced in the endpoint.");
		}
	}

	private static int findNumberOfPlaceHoldersInEndPoint(String endPoint) {
		Matcher matcher = pattern.matcher(endPoint);
		List<String> allMatches = new ArrayList<>();
		while (matcher.find()) {
			allMatches.add(matcher.group());
		}
		return allMatches.size();
	}
}
