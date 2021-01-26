package Entities;

import java.util.List;

public class Companies {

    private String symbol;
    private String name;
    private String exchange;
    private String sector;
    private int fullTimesemployees;
    private String description;
    private String city;
    private String phone;
    private String state;
    private String country;
    private String address;
    private String website;
    private List<Summary> summary;

    public Companies(String symbol, String name, String stockexchange, String sector, int fullTimeEmployees, String description, String city, String phone, String state, String country, String address, String website) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = stockexchange;
        this.sector = sector;
        this.fullTimesemployees = fullTimeEmployees;
        this.description = description;
        this.city = city;
        this.phone = phone;
        this.state = state;
        this.country = country;
        this.address = address;
        this.website = website;
    }

    public Companies(String symbol, String name, String stockexchange, String sector, List<Summary> summary) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = stockexchange;
        this.sector = sector;
        this.summary = summary;
    }

    public Companies() {
        this.symbol = null;
        this.name = null;
        this.exchange = null;
        this.sector = null;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getFullTimesemployees() {
        return fullTimesemployees;
    }

    public void setFullTimesemployees(int fullTimesemployees) {
        this.fullTimesemployees = fullTimesemployees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Summary> getSummary() {
        return summary;
    }

    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Companies{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", exchange='" + exchange + '\'' +
                ", sector='" + sector + '\'' +
                ", fullTimesemployees=" + fullTimesemployees +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
