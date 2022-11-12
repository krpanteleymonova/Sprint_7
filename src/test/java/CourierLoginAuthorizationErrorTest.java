import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CourierLoginAuthorizationErrorTest {

    private CourierClient courierClient;
    private Courier courier;
    private final Courier courierLogin;
    private int id;

    public CourierLoginAuthorizationErrorTest(Courier courierLogin) {
        this.courierLogin = courierLogin;
    }

    @Before
    public void SetUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefault();
        sendPostRequestCreateCourier();
        sendPostRequestLoginCourier();
    }

    @After
    public void CleanUp() {
        sendDeleteRequestCourier();
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{

                {CourierGenerator.getAuthorizationNoLogin()},
                {CourierGenerator.getAuthorizationNoPassword()},
                {CourierGenerator.getAuthorizationEmpty()},
        };
    }

    @Test
    @DisplayName("Ошибка авторизации существующего курьера")
    public void courierCanBeCreated() {
        sendPostRequestLoginCourierWithoutPassword();
    }

    @Step("Создание курьера")
    public void sendPostRequestCreateCourier() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        int statusCreate = responseCreate.extract().statusCode();
        System.out.println("Статус создания курьера: " + statusCreate);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 201, statusCreate);
    }

    @Step("Попытка авторизации курьера в системе без логина или пароля")
    public void sendPostRequestLoginCourierWithoutPassword() {
        ValidatableResponse responseLogin = courierClient.login(courierLogin);
        int statusLogin = responseLogin.extract().statusCode();
        System.out.println("Статус поиска id по логину: " + statusLogin);
        String message = responseLogin.extract().path("message");
        System.out.println("Ошибка авторизации: " + message);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusLogin);
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Недостаточно данных для входа", message);
    }

    @Step("Авторизация курьера в системе")
    public void sendPostRequestLoginCourier() {
        ValidatableResponse responseLogin = courierClient.login(courier);
        int statusLogin = responseLogin.extract().statusCode();
        System.out.println("Статус поиска id по логину: " + statusLogin);
        id = responseLogin.extract().path("id");
        System.out.println("id курьера: " + id);
    }

    @Step("Удаление курьера из системы")
    public void sendDeleteRequestCourier() {
        ValidatableResponse responseDelete = courierClient.delete(id);
        int statusDelete = responseDelete.extract().statusCode();
        System.out.println("Статус удаления курьера: " + statusDelete);
    }
}

