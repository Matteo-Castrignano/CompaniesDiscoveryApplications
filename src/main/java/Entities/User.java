package Entities;

public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String stringOfBirth;
    private char gender;
    private String email;
    private String country;
    protected int type_user;

    public User(String username, String password, String name, String surname, String stringOfBirth, char gender, String email, String country) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.stringOfBirth = stringOfBirth;
        this.gender = gender;
        this.email = email;
        this.country = country;
        type_user = 2;
    }
    public User(){}

    public int getType_user() {
        return type_user;
    }

    public void setType_user(int type_user) {
        this.type_user = type_user;
    }

    public String getStringOfBirth() {
        return stringOfBirth;
    }

    public void setStringOfBirth(String stringOfBirth) {
        stringOfBirth = stringOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return stringOfBirth;
    }

    public void setDateOfBirth(String StringOfBirth) {
        this.stringOfBirth = StringOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", StringOfBirth='" + stringOfBirth + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", type_user=" + type_user +
                '}';
    }
}
