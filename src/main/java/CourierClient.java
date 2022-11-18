import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String PATH_CREATE = "/api/v1/courier/";
    private static final String PATH_LOGIN = "/api/v1/courier/login";

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH_CREATE)
                .then();
    }

    public ValidatableResponse login(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH_CREATE + id)
                .then();
    }

}
