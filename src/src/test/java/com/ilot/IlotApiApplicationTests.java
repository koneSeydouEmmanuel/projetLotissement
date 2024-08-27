package com.ilot;


import org.junit.Assert;
import org.junit.Test;

import com.ilot.utils.Utilities;

import java.text.ParseException;

public class IlotApiApplicationTests {

	@Test
	public void test_method_extension() {
		final String toTest = "hello";

//.areEquals("hello")
		Assert.assertTrue(toTest.equals("hello"));

		//Assert.assertTrue(toTest.areEquals("hello"));

	}

	@Test
	public void when_date_yyyMMdd__then_ddMMyyy() throws ParseException {
		final String initialDate = "2021-12-31";
		Assert.assertEquals("31/12/2021", Utilities.formatDate(initialDate, "dd/MM/yyyy"));
		//Assert.assertEquals("31/12/2021", "");
	}
}
