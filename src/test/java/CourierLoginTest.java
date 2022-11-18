import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CourierLoginTest {

    private CourierClient courierClient;
    private Courier courier;
    private int id;

    @Before
    public void SetUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefault();
        sendPostRequestCreateCourier();
    }

    @After
    public void CleanUp() {
        sendDeleteRequestCourier();
    }

    @Test
    @DisplayName("Успешная авторизацию курьера с заполнением всех параметров")
    public void courierCanBeAuthorization() {
        sendPostRequestLoginCourier();
    }

    @Test
    @DisplayName("Попытка авторизации несуществующего курьера")
    public void courierLoginDoesNotExist() {
        sendPostRequestNonexistentLoginCourier();
    }

    @Test
    @DisplayName("Попытка авторизации курьера с некорректный паролем")
    public void courierLoginIncorrectPassword() {
        sendPostRequestIncorrectPasswordLoginCourier();
    }

    @Test
    @DisplayName("Попытка авторизации курьера с некорректный логином")
    public void courierLoginIncorrectLogin() {
        sendPostRequestIncorrectLoginCourier();
    }


    @Step("Создание курьера")
    public void sendPostRequestCreateCourier() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        int statusCreate = responseCreate.extract().statusCode();
        System.out.println("Статус создания курьера: " + statusCreate);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 201, statusCreate);
    }


    @Step("Авторизация курьера в системе")
    public void sendPostRequestLoginCourier() {
        ValidatableResponse responseLogin = courierClient.login(courier);
        int statusLogin = responseLogin.extract().statusCode();
        System.out.println("Статус поиска id по логину: " + statusLogin);
        id = responseLogin.extract().path("id");
        System.out.println("id курьера: " + id);
        Assert.assertNotNull(id);

    }

    @Step("Авторизация несуществующего курьера в системе")
    public void sendPostRequestNonexistentLoginCourier() {
        Courier nonexistentCourier = CourierGenerator.getNonexistent();
        ValidatableResponse responseLogin = courierClient.login(nonexistentCourier);
        int statusLogin = responseLogin.extract().statusCode();
        System.out.println("Статус поиска id по логину: " + statusLogin);
        String message = responseLogin.extract().path("message");
        System.out.println("Ошибка авторизации: " + message);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 404, statusLogin);
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Учетная запись не найдена", message);

    }

    @Step("Авторизация курьера c некорректным паролем")
    public void sendPostRequestIncorrectPasswordLoginCourier() {
        Courier nonexistentCourier = CourierGenerator.getNonexistentPassword();
        ValidatableResponse responseLogin = courierClient.login(nonexistentCourier);
        int statusLogin = responseLogin.extract().statusCode();
        System.out.println("Статус поиска id по логину: " + statusLogin);
        String message = responseLogin.extract().path("message");
        System.out.println("Ошибка авторизации: " + message);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 404, statusLogin);
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Учетная запись не найдена", message);

    }

    @Step("Авторизация курьера c некорректным логином ")
    public void sendPostRequestIncorrectLoginCourier() {
        Courier nonexistentCourier = CourierGenerator.getNonexistentLogin();
        ValidatableResponse responseLogin = courierClient.login(nonexistentCourier);
        int statusLogin = responseLogin.extract().statusCode();
        System.out.println("Статус поиска id по логину: " + statusLogin);
        String message = responseLogin.extract().path("message");
        System.out.println("Ошибка авторизации: " + message);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 404, statusLogin);
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Учетная запись не найдена", message);

    }

    @Step("Удаление курьера из системы")
    public void sendDeleteRequestCourier() {
        ValidatableResponse responseLogin = courierClient.login(courier);
        int statusLogin = responseLogin.extract().statusCode();
        System.out.println("Статус поиска id по логину: " + statusLogin);
        id = responseLogin.extract().path("id");
        System.out.println("id курьера: " + id);
        ValidatableResponse responseDelete = courierClient.delete(id);
        int statusDelete = responseDelete.extract().statusCode();
        System.out.println("Статус удаления курьера: " + statusDelete);
    }
}

