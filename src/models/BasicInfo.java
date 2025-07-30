package models;

public class BasicInfo {

    private Long id;

    private String lastName;

    private String firstName;

    private String phoneNumber;

    private String email;

    private UserAccount userAccount; //l'enfant reçoit le parent, dans notre projet User est l'enfant et UserAccount est le parent, où le bout de la flèche est c'est lui le parent(c'est une relation unidirectionnelle)

    //private Wallet wallet; // ici on a une relation bidirectionnele entre User et Wallet donc on créé cette ligne ici et on créé "private User user" dans la class User

    //public Wallet getWallet() {
    //return wallet;
    //}

    //public void setWallet(Wallet wallet) {
    //this.wallet = wallet;
    //}

    public BasicInfo(String lastName, String firstName, String phoneNumber, String email, UserAccount userAccount) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userAccount = userAccount;
    }

    public BasicInfo() {

    }

    public String afficherInfo() {
        return "ID: " + getId() + " | " + getLastName() + " " + getFirstName() + " " +
                getPhoneNumber() + " " + getEmail();
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
