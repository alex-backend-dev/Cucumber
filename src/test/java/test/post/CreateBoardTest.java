package test.post;

import consts.BoardsEndpoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.time.LocalDateTime;
import java.util.Map;

import static consts.BoardsEndpoints.CREATE_BOARD_URL;
import static consts.BoardsEndpoints.GET_ALL_BOARDS_URL;
import static consts.UrlParamValues.USER_NAME;

public class CreateBoardTest extends BaseTest {

    private String createdBoardId;

    @Test
    public void checkCreateBoard() {
        String boardName = "New Board" + LocalDateTime.now();
        Response response = requestWithAuth()
                .body(Map.of("name", boardName))
                .contentType(ContentType.JSON)
                .post(CREATE_BOARD_URL);
        createdBoardId = response.jsonPath().get("id");
        response
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo(boardName));
        requestWithAuth()
                .log().all()
                .pathParam("id", USER_NAME)
                .get(GET_ALL_BOARDS_URL)
                .then()
                .body("name", Matchers.hasItem(boardName));
    }

    @AfterEach
    public void deleteCreatedBoard() {
        requestWithAuth()
                .pathParam("id",createdBoardId)
                .delete(BoardsEndpoints.DELETE_BOARD_URL)
                .then()
                .statusCode(200);
    }
}
