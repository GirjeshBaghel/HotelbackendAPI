package com.test;

import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import resources.PayloadFile;

import static io.restassured.RestAssured.*;

public class testDemo extends PayloadFile{
	@Test
  public String f() {
	  
	  RestAssured.baseURI = "http://localhost:2020";
	  String resposne = given().log().all()
			  .auth().basic("girjeshbaghel@gmail.com", "girjesh@123")
			  .contentType(ContentType.JSON)
			  .body(jsonObject())
			  .when().post("/admin/addMembers")
			  .then().log().all()
			  .assertThat().statusCode(201)
			  .body("message",equalTo("Data Added"))
			  .extract().response().asString();
	  
	  return resposne;
			  
  }
}
