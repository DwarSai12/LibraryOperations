package com.test;

import io.restassured.path.json.JsonPath;

public class Reusable {
	
	public static JsonPath rawToJson(String response) {
		JsonPath js = new JsonPath (response);
		return js;
	}

}
