package test.get;

import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import static consts.UrlParamValues.EXISTING_LIST_ID;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetCardsTest extends BaseTest {

    @Test
    @Epic("Trello API")
    @Feature("Работа с досками")
    @Story("Получение списков на доске")
    @Description("Проверка успешного получения списков на доске по её ID")
    @Link(name = "Trello API Docs", url = "https://developer.atlassian.com/cloud/trello/rest/api-group-boards/")
    public void checkGetLists(){
        requestWithAuth()
                .log().all()
                .pathParam("id", "66b4f7b6289fb12a6808acfb")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .get("/boards/{id}/lists")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Story("Получить карточки списка")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @DisplayName("GET /lists/{id}/cards: у существующего списка вернёт карточки")
    public void checkGetCards() {

        Allure.parameter("listId", EXISTING_LIST_ID);

        var resp = step("Делаем запрос и проверяем статус", () ->
                given(requestWithAuth())
                        .queryParam("fields", "name,id")
                        .pathParam("id", EXISTING_LIST_ID)
                        .when()
                        .get("/lists/{id}/cards")
                        .then()
                        .statusCode(200)
                        .extract().response()
        );

        // Прикладываем prettified JSON — удобно для отладки
        Allure.addAttachment("cards.json", "application/json",
                resp.prettyPrint(), ".json");
    }

    @Test
    public void checkGetCard(){
        requestWithAuth()
                .log().all()
                .pathParam("id", "66b4f87cadb20f9d0ef8dca4")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .get("/cards/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Гости (промежуточный вариант)"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get_card.json"))
                .log().body();
    }
}
