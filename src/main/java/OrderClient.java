import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    private static final String PATH_CREATE_ORDER = "/api/v1/orders/";
     static final String PATH_TRACK_ORDER = "/api/v1/orders/cancel?track=";

    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_CREATE_ORDER)
                .then();
    }

    public ValidatableResponse cansel (int track) {
        return given()
                .spec(getSpec())
                .when()
                .put(PATH_TRACK_ORDER+track)
                .then();

    }

    public ValidatableResponse get () {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH_CREATE_ORDER)
                .then();

    }
}
