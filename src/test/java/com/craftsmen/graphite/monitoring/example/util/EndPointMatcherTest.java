package com.craftsmen.graphite.monitoring.example.util;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

public class EndPointMatcherTest {

	private EndPointMatcher endPointMatcher = new EndPointMatcher();

	@Test
	public void constructEndPointWithPlaceHolders() {
		String endPoint = "/customers/firstName/{firstName}/lastName/{lastName}";
		String[] arguments = new String[] { "John", "Doe" };
		String expectedResult = "/customers/firstName/John/lastName/Doe";
		String actualResult = endPointMatcher.constructEndPoint(endPoint, arguments);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void constructEndPointWithoutPlaceHolders() {
		String endPoint = "/customers/firstName/John/lastName/Doe";
		String[] arguments = new String[] { };
		String expectedResult = "/customers/firstName/John/lastName/Doe";
		String actualResult = endPointMatcher.constructEndPoint(endPoint, arguments);
		assertEquals(expectedResult, actualResult);
	}
	
	@Test(expectedExceptions = { RuntimeException.class }, expectedExceptionsMessageRegExp = ".* less than .*")
	public void constructEndPointExceptionNumberOfArgumentsLessThanNumberOfPlaceHolders() {
		String endPoint = "/customers/firstName/{firstName}/lastName/{lastName}";
		String[] arguments = new String[] { "John" };
		endPointMatcher.constructEndPoint(endPoint, arguments);
	}

	@Test(expectedExceptions = { RuntimeException.class }, expectedExceptionsMessageRegExp = ".* greater than .*")
	public void constructEndPointExceptionNumberOfArgumentsGreaterThanNumberOfPlaceHolders() {
		String endPoint = "/customers/firstName/{firstName}/lastName/{lastName}";
		String[] arguments = new String[] { "John", "Doe", "Jane", "Doe" };
		endPointMatcher.constructEndPoint(endPoint, arguments);
	}
}
