package niffler.api;

import niffler.model.SpendModel;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class SpendService {

    private static String addSpendUri = "http://127.0.0.1:8093";
    static final String ADD_SPEND = "/addSpend";
    public static SpendModel addSpend(SpendModel spend) {
        return given().contentType(JSON).baseUri(addSpendUri).body(spend)
                .when().post(ADD_SPEND)
                .then().statusCode(201).extract().as(SpendModel.class);
    }
}
