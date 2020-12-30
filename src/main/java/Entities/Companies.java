package Entities;

import java.util.Date;

public class Companies {

    private String name;
    private String simbol;
    private String stockExchange;
    private String sector;
    private String website;
    private String ceo;
    private int fullTimeEmployees;
    private String phone;
    private String address;
    private String city;
    private String state;
    private Date ipoDate;

    public Companies(String name, String simbol, String stockExchange, String sector, String website, String ceo, int fullTimeEmployees, String phone, String address, String city, String state, Date ipoDate) {
        this.name = name;
        this.simbol = simbol;
        this.stockExchange = stockExchange;
        this.sector = sector;
        this.website = website;
        this.ceo = ceo;
        this.fullTimeEmployees = fullTimeEmployees;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.ipoDate = ipoDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public int getFullTimeEmployees() {
        return fullTimeEmployees;
    }

    public void setFullTimeEmployees(int fullTimeEmployees) {
        this.fullTimeEmployees = fullTimeEmployees;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getIpoDate() {
        return ipoDate;
    }

    public void setIpoDate(Date ipoDate) {
        this.ipoDate = ipoDate;
    }

    @Override
    public String toString() {
        return "Companies{" +
                "name='" + name + '\'' +
                ", simbol='" + simbol + '\'' +
                ", stockExchange='" + stockExchange + '\'' +
                ", sector='" + sector + '\'' +
                ", website='" + website + '\'' +
                ", ceo='" + ceo + '\'' +
                ", fullTimeEmployees=" + fullTimeEmployees +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", ipoDate=" + ipoDate +
                '}';
    }
}
