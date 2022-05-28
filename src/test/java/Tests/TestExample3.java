package Tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestExample3 {
	
	// create user using Post API and print id 
	
	@Test
	public void test_1() {
		
		Map<String , Object> map = new HashMap <String , Object>() ;
		
		JSONObject request = new JSONObject(map);
		request.put("name","NAME");
		request.put("job","JOB");
		System.out.println(request.toJSONString());
		
		
		baseURI = "https://reqres.in/api";
		
		// extract id 
		String id1 = given().
			header("Content-Type","applicstion/json").
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(request.toJSONString()).
		when().
			post("/users").
		then().
			statusCode(201).
			extract().
			path("id");
			
		System.out.println("The returend ID is "+id1);
		System.out.println("-------------------------- ");
		
		
	}
	
	

	// Get Data of ID = 7 and print Firstname and Lastname
	
	@Test
	public void test_2() {
		
		// ID = 7 
		Response response = RestAssured.get("https://reqres.in/api/users/7");
		
		// get data from json 
		JsonPath res = 	response.jsonPath()	;
		
		String firstName = res.get("data.first_name");
		String lastName = res.getString("data.last_name");

		System.out.println("The user with ID #7 is "+firstName + " "+ lastName);
		
		// validate statuscode
		int StatusCode = response.getStatusCode();
		Assert.assertEquals(StatusCode, 200);
		System.out.println("-------------------------- ");
		
	}
	
	
	//  Negative testcase , Get Data of non exsit ID and print Firstname and Lastname
	
	@Test
	public void test_3() {
		
		// ID = 50 which not exist 
		Response response = RestAssured.get("https://reqres.in/api/users/50");
		
		// get data from json 
		JsonPath res = 	response.jsonPath()	;
		String firstName = res.get("data.first_name");
		String lastName = res.getString("data.last_name");
		
		System.out.println("First name is : "+firstName);
		System.out.println("Last name is : "+lastName);
		
		// validate statuscode 404 to show its negative 
		int StatusCode = response.getStatusCode();
		Assert.assertEquals(StatusCode, 404);
		System.out.println("-------------------------- ");
		
	}

}
