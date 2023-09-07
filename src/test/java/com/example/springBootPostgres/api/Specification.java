package com.example.springBootPostgres.api;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {
    public static RequestSpecification requestSpec(String URL){
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responceSpecOk200(){
        return (ResponseSpecification) new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification responce){
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = responce;
    }
}
