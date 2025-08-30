package test;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static consts.UrlParamValues.*;

public class BaseTest {

    @BeforeAll
    public static void setup() throws IOException {
        RestAssured.filters(new AllureRestAssured());
        FileUtils.cleanDirectory(new File("allure-results"));
        cleanAllureResults();
        RestAssured.baseURI = "https://api.trello.com/1";
    }

    protected static RequestSpecification requestWithAuth() {
        return requestWithoutAuth()
                          .queryParams(AUTH_QUERY_PARAMS)
                          .header("Accept", "application/json");
    }

    protected static RequestSpecification requestWithoutAuth() {
        return RestAssured.given();
    }

    private static void cleanAllureResults() {
        Path allureResultsDir = Paths.get("allure-results");

        if (!Files.exists(allureResultsDir)) {
            Allure.addAttachment("Allure Cleanup", "text/plain",
                    "Directory 'allure-results' does not exist. Skipping cleanup.");
            return;
        }

        try {
            // Используем FileUtils из Apache Commons IO для безопасного удаления
            FileUtils.cleanDirectory(allureResultsDir.toFile());
            Allure.addAttachment("Allure Cleanup", "text/plain",
                    "Successfully cleaned 'allure-results' directory.");
        } catch (Exception e) {
            Allure.addAttachment("Allure Cleanup Error", "text/plain",
                    "Failed to clean 'allure-results': " + e.getMessage());
            throw new RuntimeException("Failed to clean Allure results directory", e);
        }
    }

}
