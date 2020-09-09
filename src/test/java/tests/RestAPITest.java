package tests;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Endpoints;
import utils.TestParamsParser;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAPITest {

    private static final String BASE_URI = "https://reqres.in/";
    private static final String BASE_PATH = "/api";
    private static final String TEST_USER_ID = "2";

    private Map<String, String> testParams;
    private User testUser;

    @BeforeClass
    private void setUp() {
        RestAssured.requestSpecification = buildRequestSpec();
        testParams = TestParamsParser.getParams();
        testUser = new User(testParams.get("name"), testParams.get("job"));
    }

    @Description("Verifies user creation with name and job specified in XML file")
    @Test
    public void testCreateUser() {
        given()
                .body(testUser)
        .when()
                .post(Endpoints.users)
        .then()
                .statusCode(201)
            .and()
                .body("name", equalTo(testUser.getName()))
                .body("job", equalTo(testUser.getJob()));
    }

    @Description("Verifies user updating with newJob specified in XML file")
    @Test
    public void testUpdateUser() {
        String newJob = testParams.get("newJob");
        Map<String, String> updateData = new HashMap<>();
        updateData.put("job", newJob);

        given()
                .body(updateData)
        .when()
                .patch(Endpoints.userWithId, TEST_USER_ID)
        .then()
                .statusCode(200)
            .and()
                .body("job", equalTo(newJob));
    }

    @Description("Verifies user deletion")
    @Test
    public void testDeleteUser() {
        given()
        .when()
                .delete(Endpoints.userWithId, TEST_USER_ID)
        .then()
                .statusCode(204);
    }

    private RequestSpecification buildRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

}
