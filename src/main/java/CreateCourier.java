public class CreateCourier {

    public String login;
    public String password;
    public String firstName;

    public CreateCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CreateCourier(String login, String password) {
        this.login = login;
        this.password = password;

    }
    public CreateCourier(String password) {
        this.password = password;

    }
    public CreateCourier() {


    }
}
