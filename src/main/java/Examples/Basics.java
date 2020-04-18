package Examples;

import org.jvnet.staxex.StAxSOAPBody.Payload;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

//given : all input details
//when : submit the API-resource,http method
//then : validate the response
//log all: shows all the request and response details


public class Basics {
	PayLoad payload = new PayLoad();
	@Test
	public void addPlace() {
	RestAssured.baseURI="https://rahulshettyacademy.com";
	given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
	.body(payload.)
	.when().post("/maps/api/place/add/json")
	.then().log().all().assertThat().statusCode(200)
	.body("scope",equalTo("APP"))
	.header("server", equalTo("Apache/2.4.18 (Ubuntu)"));
	}
}
