package Entities;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private char gender;
    private String email;
    private String country;

    private int followedUser;       /*solo numero di utenti seguiti*/
    private int followedCompanies;  /*solo numero di aziende seguite*/

    public User(String username, String password, String name, String surname, Date dateOfBirth, char gender, String email, String country) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.country = country;
        this.followedUser = 0;
        this.followedCompanies = 0;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public int getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(int followedUser) {
        this.followedUser = followedUser;
    }

    public int getFollowedCompanies() {
        return followedCompanies;
    }

    public void setFollowedCompanies(int followedCompanies) {
        this.followedCompanies = followedCompanies;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", followedUser=" + followedUser +
                ", followedCompanies=" + followedCompanies +
                '}';
    }
}
