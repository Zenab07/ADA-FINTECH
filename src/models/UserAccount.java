package models;

import models.enumeration.CompteType;

public class UserAccount {

    private Long id;

    private String login;

    private String password;

    private CompteType compteType;



    public UserAccount(Long id, String login, String password, CompteType compteType) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.compteType = compteType;

    }

    public UserAccount(String login, String password, CompteType compteType) {
        this.login = login;
        this.password = password;
        this.compteType = compteType;
    }

    public UserAccount(String login) {
        this.login = login;
    }

    public UserAccount() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompteType getCompteType() {
        return compteType;
    }

    public void setCompteType(CompteType compteType) {
        this.compteType = compteType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "WELCOME ID :" +id + " " + login + " ["+compteType+"]";
    }


}
