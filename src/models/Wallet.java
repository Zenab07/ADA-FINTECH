package models;

import java.math.BigDecimal;

public class Wallet {

    private Long id;

    private BigDecimal balance;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() { // les getteurs permettent de lire la valeur d'un attribut privé
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUserAccount(UserAccount clientAccount) {
    }
}
