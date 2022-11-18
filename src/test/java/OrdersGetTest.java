import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrdersGetTest {
    private OrderClient orderClient;

    @Before
    public void SetUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void OrderGet() {
        ValidatableResponse responseOrderGet = orderClient.get();
        int statusGetOrder = responseOrderGet.extract().statusCode();
        System.out.println("Статус создания заказа: " + statusGetOrder);
        int id = responseOrderGet.extract().path("orders[0].id");
        System.out.println(id);
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusGetOrder);
        Assert.assertNotNull(id);

    }

}
