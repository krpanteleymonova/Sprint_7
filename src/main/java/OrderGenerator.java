import java.util.List;

public class OrderGenerator {
    public static Order getOrderColorBlack() {
        return new Order(
                "Василий",
                "Иванов",
                "Мира,24",
                "4",
                "+7 908 355 35 35",
                5,
                "2022-11-12",
               "",
                List.of("BLACK")
        );

    }
    public static Order getOrderColorGrey() {
        return new Order(
                "Василий",
                "Иванов",
                "Мира,24",
                "4",
                "+7 908 355 35 35",
                5,
                "2022-11-12",
                "",
                List.of("GREY")
        );

    }
    public static Order getOrderColorBlackAndGrey() {
        return new Order(
                "Василий",
                "Иванов",
                "Мира,24",
                "4",
                "+7 908 355 35 35",
                5,
                "2022-11-12",
                "",
                List.of("BLACK","GREY")
        );

    }
    public static Order getOrderColorEmpty() {
        return new Order(
                "Василий",
                "Иванов",
                "Мира,24",
                "4",
                "+7 908 355 35 35",
                5,
                "2022-11-12",
                "",
                List.of()
        );

    }
}
