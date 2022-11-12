import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CourierCreateNegativeTest {

    private CourierClient courierClient;
    private final Courier courier;

    public CourierCreateNegativeTest(Courier courier) {
        this.courier = courier;
    }

    @Before
    public void SetUp() {
        courierClient = new CourierClient();

    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierGenerator.getNoPassword()},
                {CourierGenerator.getNoLogin()},
                {CourierGenerator.getNoLoginAndPassword()},
                {CourierGenerator.getLoginEmpty()},
        };
    }

    @Test
    @DisplayName("Ошибка при создании учетной записи без заполнения всех параметров")
    public void courierCanNotBeCreated() {
        sendPostRequestCreateCourier();
        sendPostRequestLoginCourier();
    }

    @Step("Создание курьера без обязательных параметров")
    public void sendPostRequestCreateCourier() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        int statusCreate = responseCreate.extract().statusCode();
        System.out.println("Статус создания курьера: " + statusCreate);
        String messageCreateSecond = responseCreate.extract().path("message");
        System.out.println("Сообщение об ошибке: " + messageCreateSecond);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusCreate);
        Assert.assertEquals("Сообщение об ошибке при создании курьера не соответствует ожидаемому", "Недостаточно данных для создания учетной записи", messageCreateSecond);

    }

    @Step("Поиск логина курьера в системе")
    public void sendPostRequestLoginCourier() {
        ValidatableResponse responseLogin = courierClient.login(courier);
        int statusLogin = responseLogin.extract().statusCode();
        System.out.println("Статус поиска id по логину: " + statusLogin);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusLogin);

    }

}

