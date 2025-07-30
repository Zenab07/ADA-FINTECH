package models;

//public class Customer extends BasicInfo { // à cause de l'héritage on met "extends", Customer hérite de User

import models.enumeration.Gender;

public class Customer extends User{
    private Gender gender;



    public Customer(String lastName, String firstName, String phone, String email, Gender gender, UserAccount userAccount, Wallet wallet, String profilePage) {
        super(lastName, firstName, phone, email, userAccount, profilePage, wallet);
        this.gender = gender;
    }

    public Customer() {
        super();
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
