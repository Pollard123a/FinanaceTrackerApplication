package com.finance.FinanceTraker.header;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

public class RestAPIHeader {

    @Test
    public void testHeader() {

        String url = "http://localhost:8080/23";

        RequestSpecification httpRequest = RestAssured.given()
                .headers("Content-Type","application/json");

        Response response = httpRequest.get(url);

        int statusCode = response.getStatusCode();

        ResponseBody body = response.body();

        System.out.println("Status code is: "+statusCode);

        System.out.println("Body is "+body.print());
    }
}
