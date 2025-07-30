package models;

public class User extends BasicInfo{

    private String profilePage;

    private Wallet wallet;


    public User(String lastName, String firstName, String phone, String email, UserAccount userAccount, String profilePage, Wallet wallet) {
        super(lastName, firstName, phone, email, userAccount);
        this.profilePage = profilePage;
        this.wallet = wallet;
    }

    public User() {
        super();
    }

    public String getProfilePage() {
        return profilePage;
    }

    public void setProfilePage(String profilePage) {
        this.profilePage = profilePage;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
