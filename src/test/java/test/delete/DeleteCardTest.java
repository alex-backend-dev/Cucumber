package test.delete;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.util.Map;

import static consts.BoardsEndpoints.*;
import static consts.UrlParamValues.EXISTING_LIST_ID;

public class DeleteCardTest extends BaseTest {

    private String createCardId;

    @BeforeEach
    public void createCard(){
        createCardId = requestWithAuth()
                .body(Map.of(
                        "idList", EXISTING_LIST_ID,
                        "name", "New One")
                )
                .contentType(ContentType.JSON)
                .post(CREATE_CARD_URL)
                .body().jsonPath().get("id");
    }

    @Test
    public void checkDeleteCard(){
        requestWithAuth()
                .pathParam("id", createCardId)
                .delete(DELETE_CARD_URL)
                .then()
                .statusCode(200)
                .body("_value", Matchers.equalTo(null));
        requestWithAuth()
                .log().all()
                .pathParam("id", EXISTING_LIST_ID)
                .queryParams("fields", "name,id")
                .get(GET_ALL_CARDS_URL)
                .then()
                .body("id", Matchers.not(Matchers.hasItem(createCardId)));
    }
}
