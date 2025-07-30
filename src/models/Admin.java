package models;

import models.enumeration.*;

import java.util.List;

public class Admin extends BasicInfo{

    private List<Privileges> privileges;

    public Admin(String lastName, String firstName, String phone, String email, UserAccount userAccount, List<Privileges> privileges) {
        super(lastName, firstName, phone, email, userAccount);
        this.privileges = privileges;
    }

    public Admin() {

    }

    public List<Privileges> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privileges> privileges) {
        this.privileges = privileges;
    }

    public String afficherUser() {
        return afficherInfo() + " | ["+ CompteType.ADMIN +"]"+getPrivileges();
    }


}
