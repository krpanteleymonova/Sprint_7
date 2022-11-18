import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private OrderClient orderClient;
    private int track;
    private List<String> color;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Before
    public void SetUp() {
        orderClient = new OrderClient();

    }


    @After
    public void CleanUp() {
        sendPutCanselRequestOrder();
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }


    @Test
    @DisplayName("Успешное создание заказа")
    public void OrderCanBeCreated() {
        sendPostRequestCreateOrder();
    }

    @Step("Создать заказ")
    public void sendPostRequestCreateOrder() {
        Order order = OrderGenerator.OrderGetColor(color);
        ValidatableResponse responseCreateOrder = orderClient.create(order);
        int statusOrder = responseCreateOrder.extract().statusCode();
        System.out.println("Статус создания заказа: " + statusOrder);
        track = responseCreateOrder.extract().path("track");
        System.out.println("track заказа: " + track);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 201, statusOrder);
        Assert.assertNotNull(track);
    }


    @Step("Отменить заказ")
    public void sendPutCanselRequestOrder() {
        System.out.println(OrderClient.PATH_TRACK_ORDER + track);
        ValidatableResponse responseOrderCansel = orderClient.cansel(track);
        int statusOrderCansel = responseOrderCansel.extract().statusCode();
        System.out.println("Статус выполнения запроса по отмене заказа " + statusOrderCansel);

    }
}
