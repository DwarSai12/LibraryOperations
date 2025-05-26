package com.test.resources;

import io.restassured.path.json.JsonPath;

public class Reusable {
	
	public static JsonPath rawToJson(String response) {
		JsonPath js = new JsonPath (response);
		return js;
	}

}
