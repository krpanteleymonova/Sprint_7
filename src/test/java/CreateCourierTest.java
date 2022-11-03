import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static io.restassured.RestAssured.given;
import java.util.Random;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    String[] login = new String[]{"Try", "tester666", "ASSAY", "c_h_a_n_c_e"}; // массив логинов
    String[] password = new String[]{"123456", "654321", "987654", "456789"};//массив паролей
    int randomLogin = new Random().nextInt(login.length); // выбор случайной компании из массива
    int randomPassword = new Random().nextInt(password.length);
    int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Успешное создание учетной записи c заполнением всех параметров") // имя теста
    public void CreateCourierStatusCode() {
        System.out.println("Создаем курьера с параметрами: "+login[randomLogin]+" "+password[randomPassword]+" "+login[randomLogin] );
        CreateCourier json = new CreateCourier(login[randomLogin], password[randomPassword], login[randomLogin]);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        System.out.println(response.body().asString());
        response.then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));

        System.out.println("Узнаем ID записи:");
        CourierGetId();
        System.out.println("Удаляем запись по ID");
        DeleteCourierStatusCode();
    }

    @Test
    @DisplayName("Невозможно создать двух одинаковых курьеров")
    public void CreateSameCourierError() {
        System.out.println("Создаем курьера с параметрами: "+login[randomLogin]+" "+password[randomPassword]+" "+login[randomLogin] );
        CreateCourier json = new CreateCourier(login[randomLogin], password[randomPassword], login[randomLogin]);
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier")
                        .then().statusCode(201);
        System.out.println("Узнаем ID записи:");
        CourierGetId();
        System.out.println("Повторно создаем курьера с параметрами: "+login[randomLogin]+" "+password[randomPassword]+" "+login[randomLogin] );
        Response response =     given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier");
        System.out.println("Результат запроса: "+ response.body().asString());
        response.then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        System.out.println("Удаляем запись по ID");
        DeleteCourierStatusCode();
    }

    @Test
    @DisplayName("Курьер создается при заполнении только обязательных полей  login и password") // имя теста
    public void CreateCourierWithoutFirstNameStatusCode() {
        System.out.println("Создаем курьера с параметрами: "+login[randomLogin]+" "+password[randomPassword] );
        CreateCourier json = new CreateCourier(login[randomLogin], password[randomPassword]);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        System.out.println("Результат запроса: " +response.body().asString());
        response.then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
        System.out.println("Курьер создан.");
        System.out.println("Узнаем ID записи:");
        CourierGetId();
        System.out.println("Удаляем запись по ID");
        DeleteCourierStatusCode();
    }

    @Test
    @DisplayName("Курьер  не создается, если не заполенео обязательное поле  login, поле password заполнено ") // имя теста
    public void CreateCourierWithoutLoginError() {
        System.out.println("Создаем курьера с параметрами: "+password[randomPassword] );
        CreateCourier json = new CreateCourier(password[randomPassword]);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        System.out.println("Результат запроса: " +response.body().asString());
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }


    @Test
    @DisplayName("Курьер  не создается, если не заполено обязательное поле password ") // имя теста
    public void CreateCourierWithoutPasswordError() {
        System.out.println("Создаем курьера с параметрами: "+login[randomLogin]);
        CourierConstructor json = new CourierConstructor(login[randomLogin]);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        System.out.println("Результат запроса: " +response.body().asString());
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
    @Test
    @DisplayName("Курьер  не создается, если не заполены обязательные поля") // имя теста
    public void CreateCourierWithoutLoginAndPasswordError() {
        CreateCourier json = new CreateCourier();
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        System.out.println("Результат запроса: " +response.body().asString());
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    public void CourierGetId() {
        LoginCourier json = new LoginCourier(login[randomLogin], password[randomPassword]);
        id = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(200)
                .and()
                .extract().body().path("id");
        System.out.println("ID записи: "+id);
    }

    public void DeleteCourierStatusCode() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete("/api/v1/courier/" + id);
        response.then().statusCode(200);
        System.out.println(response.body().asString());

    }
}
