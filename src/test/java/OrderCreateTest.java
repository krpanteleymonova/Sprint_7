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
public class OrderCreateTest {

    private OrderClient orderClient;
    private final Order order;
    private int track;

    public OrderCreateTest(Order order) {
        this.order = order;
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

                {OrderGenerator.getOrderColorBlack()},
                {OrderGenerator.getOrderColorGrey()},
                {OrderGenerator.getOrderColorBlackAndGrey()},
                {OrderGenerator.getOrderColorEmpty()},
        };
    }

    @Test
    @DisplayName("Успешное создание заказа")
    public void OrderCanBeCreated() {
        sendPostRequestCreateOrder();
    }

    @Step("Создать заказ")
    public void sendPostRequestCreateOrder() {
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
