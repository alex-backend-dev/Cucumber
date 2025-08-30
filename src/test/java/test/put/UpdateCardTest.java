package test.put;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.time.LocalDateTime;
import java.util.Map;

import static consts.BoardsEndpoints.*;
import static consts.UrlParamValues.CARD_ID_TO_UPDATE;

public class UpdateCardTest extends BaseTest {

    @Test
    public void checkUpdateCard(){
        String updatedName = "Update Name" + LocalDateTime.now();
        ValidatableResponse response = requestWithAuth()
                .pathParam("id", CARD_ID_TO_UPDATE)
                .body(Map.of("name", updatedName))
                .contentType(ContentType.JSON)
                .put(UPDATE_CARD_URL)
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo(updatedName));
        requestWithAuth()
                .pathParam("id", CARD_ID_TO_UPDATE)
                .get(GET_CARD_URL)
                .then()
                .body("name", Matchers.equalTo(updatedName));
    }
}
