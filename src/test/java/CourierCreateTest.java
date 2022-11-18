import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CourierCreateTest {

    private CourierClient courierClient;
    private Courier courier;
    private int id;

    @Before
    public void SetUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefault();

    }

    @After
    public void CleanUp() {
        sendDeleteRequestCourier();
    }

    @Test
    @DisplayName("Успешное создание учетной записи c заполнением всех параметров")
    public void courierCanBeCreated() {
        sendPostRequestCreateCourier();
        sendPostRequestLoginCourier();
    }

    @Test
    @DisplayName("Cоздание двух одинаковых учетных записей невозможно")
    public void courierCanNotBeCreatedSecond2() {
        sendPostRequestCreateCourier();
        sendPostRequestLoginCourier();
        sendPostRequestCreateCourierSecond();

    }

    @Step("Создание курьера")
    public void sendPostRequestCreateCourier() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        int statusCreate = responseCreate.extract().statusCode();
        System.out.println("Статус создания курьера: " + statusCreate);
        boolean okCreate = responseCreate.extract().path("ok");
        System.out.println("Курьер создан: " + okCreate);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 201, statusCreate);
        Assert.assertTrue("Статус не соответствует ожидаемому", okCreate);

    }

    @Step("Повторное создание курьера")
    public void sendPostRequestCreateCourierSecond() {
        ValidatableResponse responseCreateSecond = courierClient.create(courier);
        int statusCreateSecond = responseCreateSecond.extract().statusCode();
        String messageCreateSecond = responseCreateSecond.extract().path("message");
        System.out.println("Статус создания курьера: " + statusCreateSecond);
        System.out.println("Сообщение об ошибке при создании курьера: " + messageCreateSecond);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 409, statusCreateSecond);
        Assert.assertEquals("Сообщение об ошибке при создании курьера не соответствует ожидаемому", "Этот логин уже используется. Попробуйте другой.", messageCreateSecond);
    }

    @Step("Поиск логина курьера в системе")
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

