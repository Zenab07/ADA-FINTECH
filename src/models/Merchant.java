package models;

public class Merchant extends User {

    private String location;


    public Merchant(String lastName, String firstName, String phone, String email, String location, UserAccount userAccount, Wallet wallet, String profilePage) {
        super(lastName, firstName, phone, email, userAccount, profilePage, wallet);
        this.location = location;
    }

    public Merchant() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
