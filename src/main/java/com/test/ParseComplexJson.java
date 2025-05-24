package com.test;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ParseComplexJson {
	
	public static void main (String args[]) {
	
	JsonPath js = Reusable.RawToJson(Payload.BookPrice());	
	
	//1. Print No of courses returned by API
	
	int count = js.getInt("courses.size()");

	System.out.println("Number of cources are : "+count);
	
	System.out.println("\n///////////////////////////////////////////////////////////////////\n");
	//2.Print Purchase Amount
	
	int purchaseAmount = js.getInt("dashboard.purchaseAmount");
	
	System.out.println("Purchase Amount is : "+ purchaseAmount);
	
	System.out.println("\n///////////////////////////////////////////////////////////////////\n");
	//3. Print Title of the first course
	
	String title = js.get("courses[0].title");
	System.out.println("Title of first course is : "+title);
	
	System.out.println("\n///////////////////////////////////////////////////////////////////\n");
	//4. Print All course titles and their respective Prices
	
	System.out.println("Here are cource details :");
	for(int i=0;i<count;i++) {
	String CourseTitle = js.get("courses["+i+"].title");
	String CoursePrice = js.get("courses["+i+"].price").toString();
	
	System.out.println("Title : " + CourseTitle );
	System.out.println("Price : " + CoursePrice );
	
	}
	
	System.out.println("\n///////////////////////////////////////////////////////////////////\n");
	//5. Print no of copies sold by RPA Course
	for(int i=0;i<count;i++) {
		String CourseTitle = js.get("courses["+i+"].title");
		if(CourseTitle.equals("RPA"))
		{ 
		String copies=	js.get("courses["+i+"].copies").toString();
		System.out.println("Number of copies sold by RPA : " + copies );
		break;
		}
	}
	
	System.out.println("\n///////////////////////////////////////////////////////////////////\n");
	//6. Verify if Sum of all Course prices matches with Purchase Amount
	int SumOfAmount = 0;
	for(int i=0;i<count;i++) {
		String CourseTitle = js.get("courses["+i+"].title");
		int CoursePrice = js.get("courses["+i+"].price");
		int CourseCopies = js.get("courses["+i+"].copies");
	    int  totalAmount = CoursePrice * CourseCopies;
	    System.out.println("Total amount for cource"+ CourseTitle+ ":" +totalAmount);
	    SumOfAmount = SumOfAmount + totalAmount;
	    }
	System.out.println("--------------------------------------");
	System.out.println("Total amount for all cources : " + SumOfAmount);

	Assert.assertEquals(SumOfAmount, purchaseAmount);
	
	
	
}}
