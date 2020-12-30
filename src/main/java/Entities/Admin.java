package Entities;

import java.util.Date;

public class Admin extends User {

    private final boolean superuser;

    public Admin(String username, String password, String name, String surname, Date dateOfBirth, char gender, String email, String country) {
        super(username, password, name, surname, dateOfBirth, gender, email, country);
        this.superuser=true;
    }
}
