package com.test;

import io.restassured.path.json.JsonPath;

public class Reusable {
	
	public static JsonPath RawToJson(String response) {
		JsonPath js = new JsonPath (response);
		return js;
	}

}
