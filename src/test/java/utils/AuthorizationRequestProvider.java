package utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static consts.UrlParamValues.AUTH_QUERY_PARAMS;

public class AuthorizationRequestProvider {

    public static RequestSpecification requestWithAuth() {
        return requestWithoutAuth()
                .queryParams(AUTH_QUERY_PARAMS)
                .header("Accept", "application/json");
    }

    public static RequestSpecification requestWithoutAuth() {
        RestAssured.baseURI = "https://api.trello.com/1";
        return RestAssured.given();
    }
}
