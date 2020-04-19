package Examples;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import payloadFiles.PayLoad;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

//given : all input details
//when : submit the API-resource,http method
//then : validate the response
//log all: shows all the request and response details

public class Basics {
	PayLoad payLoad = new PayLoad();
	String placeid="";
	final String BASE_URI = "https://rahulshettyacademy.com";
	final String newAdress= "70 Summer walk, USA";

	RequestSpecification httpRequest;
	JsonPath json;
	
	@BeforeClass
	public void setup() {
		// Setting BaseURI once
		RestAssured.baseURI = BASE_URI;
		 httpRequest = RestAssured.given();
	}

	@Test(priority=1)
	public void addPlace() {
		//RequestSpecification httpRequest = RestAssured.given();

		String response = httpRequest.log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(payLoad.addPlayload()).when().post("/maps/api/place/add/json").then()
				.log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server", equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();
		System.out.println(response);

		 json = new JsonPath(response);// convert string to json
		 placeid = json.get("place_id");
		System.out.println("addPlace : ==========================================" + placeid);
	}
	
	@Test(priority=2)
	public void updatePlace() {
		System.out.println("updatePlace : ==========================================" + placeid);
		httpRequest.log().all().queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
		.body("{\n" + 
				"\"place_id\":\""+placeid+"\",\n" + 
				"\"address\":\""+newAdress+"\",\n" + 
				"\"key\":\"qaclick123\"\n" + 
				"}\n" + 
				"")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	}
	@Test(priority=3)
	public void getAddress() {
		System.out.println("getAddress : ==========================================" + placeid);
		String response= httpRequest.log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeid)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract()
		.response().asString();
		json = new JsonPath(response);
		String actualAddress= json.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAdress);
		
	}

}
