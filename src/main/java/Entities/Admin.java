package Entities;

import java.util.Date;

public class Admin extends User {

    public Admin(String username, String password, String name, String surname, String stringOfBirth, char gender, String email, String country) {
        super(username, password, name, surname, stringOfBirth, gender, email, country);
        type_user = 0;
    }
}
