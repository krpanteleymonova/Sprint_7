import java.sql.Timestamp;
public class CourierGenerator {
    static Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    private static final String login = "kpanteleymonova_"+ timestamp;
    private static final String password = String.valueOf(timestamp);
    private static final String firstName = "kpanteleymonova";
    private static final String nonexistent = "nonexistent";

    public static Courier getDefault() {
        return new Courier(login, password, firstName);
    }

    public static Courier getNoLogin() {
        return new Courier("", password, firstName);
    }

    public static Courier getAuthorizationNoLogin() {
        return new Courier("", password);
    }

    public static Courier getAuthorizationNoPassword() {
        return new Courier(login, "");
    }

    public static Courier getAuthorizationEmpty() {
        return new Courier("", "");
    }

    public static Courier getNoPassword() {
        return new Courier(login, "", firstName);
    }

    public static Courier getNoLoginAndPassword() {
        return new Courier("", "", firstName);
    }

    public static Courier getLoginEmpty() {
        return new Courier("", "", "");
    }

    public static Courier getNonexistent() {
        return new Courier(nonexistent, nonexistent);
    }


    public static Courier getNonexistentLogin() {
        return new Courier(nonexistent, password);
    }

    public static Courier getNonexistentPassword() {
        return new Courier(login, nonexistent);
    }
}
