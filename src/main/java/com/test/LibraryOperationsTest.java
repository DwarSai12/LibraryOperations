package com.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class LibraryOperationsTest {
	
	//Created list to store ID created by addBook method
	static List<String> bookID = new ArrayList<String>();
	
	
	@BeforeMethod
	@Parameters({"setEnvironment"})
	public void setupURI(String setEnvironment) {
		RestAssured.baseURI =setEnvironment.toString();
	}
	
	//AddBook  
	@Test (dataProvider = "TDAddBook", groups= "UpdateBookData")
	public void addBook(String isbn, int aisle) {
		
		String RespAddBook = given().log().all().body(Payload.addBook(isbn, aisle))
							.when().post("/Library/Addbook.php")
							.then().log().all().assertThat().statusCode(200)
								.extract().response().asString();
	
	   JsonPath js = Reusable.rawToJson(RespAddBook);
	   
	   String ID = js.getString("ID");
	   String Msg = js.getString("Msg");
	   System.out.println(ID);
	   bookID.add(ID);
	   
	   //Validate result 
	   Assert.assertEquals(Msg, "successfully added");
	}
	   
	//GetBook by ID 
	@Test (dependsOnMethods="addBook", dataProvider= "BookID", groups= "UpdateBookData",priority=1)
	public void getBook(String ID) {
		String RespUpdateBook=given().log().all().param("ID", bookID)
		.when().get("Library/GetBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//JsonPath js = Reusable.RawToJson(RespUpdateBook);
		//String UdateBookID = js.getString("ID");
		
	}
	
	
	
	// DeleteBook which is depends on addBook method to get ID
	@Test (dependsOnMethods="addBook", dataProvider= "BookID", groups= "Delete-BookData", priority=2)
	public void deleteBook(String ID) {
		String RespDeleteBook=given().log().all().body(Payload.deleteBook(ID))
		.when().delete("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = Reusable.rawToJson(RespDeleteBook);
		String Message = js.getString("msg");
		
		//validate result 
		Assert.assertEquals(Message, "book is successfully deleted");
	}
	
	//Data provider addBook to create unique book ID 
	@DataProvider(name = "TDAddBook")
	public Object[][] getDataAddBook() {
		return new Object[][] {{"def",1120},{"ghk",1121},{"hij",1122}} ;
	}
	
	//Data provider deleteBook to pass all ID created by addBook method
	@DataProvider(name = "BookID")
	public Object[] getDataDeleteBook() {
	    Object[] data = new Object[bookID.size()];
	    
	    for (int i = 0; i < bookID.size(); i++) {
	        data[i] = bookID.get(i);
	    }
	    
	    return data;
	}
	
	
}
