import com.github.javafaker.Faker;

import java.util.List;

public class OrderGenerator {
    static Faker faker = new Faker();

    public static Order getOrderColorDefolt() {
        return new Order(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().streetAddress(),
                String.valueOf(faker.number().numberBetween(1, 10)),
                faker.phoneNumber().phoneNumber(),
                faker.number().numberBetween(1, 10),
                "2022-11-12",
                "",
                List.of()
        );
    }
        public static Order OrderGetColor(List<String> color) {
          Order order=getOrderColorDefolt();
          order.setColor(color);
        return order;

    }

}
