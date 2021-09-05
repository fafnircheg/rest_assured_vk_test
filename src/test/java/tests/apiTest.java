package tests;


import io.qameta.allure.Feature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * My homework with VK API and rest-assured
 * @autor Alexander Ilyushin
 * @version 0.01
 */
@DisplayName("Тесты работы с API клиента")
@Feature("Api for users")
public class apiTest {

    private static final String BASE_URI = "https://api.vk.com/method";

    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.ANY)
            .build();

    @BeforeAll
    public static void startTestScope(){
        System.out.println("Start VK API testing");
    }

    @BeforeEach
    public void printTestNameBefore(TestInfo info) {
        Helpers.printTestNameWithState(info.getDisplayName(), "start");
    }

    /**
     * Hide birthday date
     */
    @Test
    @DisplayName("Hide birthday date")
    public void hideBirthdayDate() {
        String urlPost = EndPoints.getConstructedPath("account", "saveProfileInfo", "bdate_visibility=0");
        Response res = given()
                .spec(requestSpec)
                .post(urlPost);
        String expected = String.format("{\"response\":{\"changed\":1}}");
        System.out.println(res.asString());
        assertEquals(expected, res.asString(), Helpers.getMethodName());
        assertEquals(200, res.getStatusCode(), Helpers.getMethodName());
    }

    /**
     * Get Current user's main info - parameterized test
     * @param params - fields that should be contained in response
     */
    @ParameterizedTest
    @ValueSource(strings = {"bdate", "city,country", "screen_name,relation"})
    @DisplayName("Get Current user's main info")
    public void getCurrentUserInfo(String params) {
        String[] separateParams = params.split(",");
        String urlGet = EndPoints.getConstructedPath("users", "get", "fields=" + params);
        Response res = given()
                .spec(requestSpec)
                .get(urlGet);
        assertTrue(Helpers.containsAllWords(res.asString(), separateParams), Helpers.getMethodName());
    }

    /**
     * Check that Pavel Durov is not my mom friend, but my wife is her friend
     */
    @Test
    @DisplayName("Check that Pavel Durov is not my friend, but my wife is my friend")
    public void areFewPeopleFriends() {
        String urlGet = EndPoints.getConstructedPath("friends", "areFriends", "", "23866308,1");
        Response res = given()
                .spec(requestSpec)
                .get(urlGet);
        String expected = String.format("{\"response\":[{\"friend_status\":3,\"user_id\":23866308},{\"friend_status\":0,\"user_id\":1}]}");
        assertEquals(expected, res.asString(), Helpers.getMethodName());
    }

    /**
     * Get Current user's main info - parameterized test
     * @param functionName - function names. setOnline - valid name, setChotaOnline - invalid
     */
    @ParameterizedTest
    @ValueSource(strings = {"setOnline", "setChotaOnline"})
    @DisplayName("Set current user online")
    public void setCurrentUserOnline(String functionName) {
        String urlGet = EndPoints.getConstructedPath("account", functionName, "");
        Response res = given()
                .spec(requestSpec)
                .get(urlGet);
        if (functionName.equals("setOnline")) {
            String expected = String.format("{\"response\":1}");
            assertEquals(expected, res.asString(), Helpers.getMethodName());
        } else {
            assertEquals("Unknown method passed", res.getBody().jsonPath().get("error.error_msg"), Helpers.getMethodName());
        }
    }

    @AfterEach
    public void printTestNameAfter(TestInfo info) {
        Helpers.printTestNameWithState(info.getDisplayName(), "finish");
    }

    @AfterAll
    public static void finishTestScope(){
        System.out.println("Finish VK API testing");
    }
}
