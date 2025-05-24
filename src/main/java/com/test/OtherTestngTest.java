package com.test;

import org.testng.annotations.Test;

public class OtherTestngTest {

	// Executing TC multiple times 
	@Test(invocationCount =10, groups= "OtherTC")
	public void invocationCountTest() {
		int a=1;
		int b=12;
		int c = a+b;
		System.out.println("Sum is : "+c);
	}
	
	//Expected exception
	@Test (expectedExceptions=NumberFormatException.class, groups= "OtherTC")
	public void convertData() {
		String x ="200A";
		Integer.parseInt(x);
	}
}
