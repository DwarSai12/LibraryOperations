package com.test.resources;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert; // 

public class Basics {

	public static void main(String[] args) {
		// Base URI
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		Reusable rs = new Reusable();

		// Add Place API
		String response = given().log().all().queryParam("key", "qaclick123").body(Payload.addPlace()).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		JsonPath js = rs.rawToJson(response);
		String place_id = js.getString("place_id");
		System.out.println("Full Response: " + response);
		System.out.println("Extracted Place ID: " + place_id);

		System.out.println("\n////////////////////////////////////////////////////////////////////\n");

		// Update Place API
		String newAddress = "70 winter walk, USA";

		given().log().all().queryParam("key", "qaclick123")
				.body("{\r\n" + "\"place_id\":\"" + place_id + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		System.out.println("\n///////////////////////////////////////////////////////////////////\n");

		// Get Place API
		String response2 = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
							.when()
							.get("maps/api/place/get/json")
							.then().assertThat().log().all().statusCode(200).extract().response()
							.asString();

		JsonPath js1 = rs.rawToJson(response2);
		String address = js1.getString("address");
		System.out.println("Extracted Address: " + address);

		Assert.assertEquals(address, newAddress);

	}
}
