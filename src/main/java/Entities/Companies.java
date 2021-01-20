package Entities;

public class Companies {

    private String symbol;
    private String name;
    private String stockExchange;
    private String sector;
    private int fullTimeEmployees;
    private String description;
    private String city;
    private String phone;
    private String state;
    private String country;
    private String address;
    private String website;

    public Companies(String symbol, String name, String stockExchange, String sector, int fullTimeEmployees, String description, String city, String phone, String state, String country, String address, String website) {
        this.symbol = symbol;
        this.name = name;
        this.stockExchange = stockExchange;
        this.sector = sector;
        this.fullTimeEmployees = fullTimeEmployees;
        this.description = description;
        this.city = city;
        this.phone = phone;
        this.state = state;
        this.country = country;
        this.address = address;
        this.website = website;
    }

    public Companies(String symbol, String name, String stockExchange, String sector) {
        this.symbol = symbol;
        this.name = name;
        this.stockExchange = stockExchange;
        this.sector = sector;
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

    public int getFullTimeEmployees() {
        return fullTimeEmployees;
    }

    public void setFullTimeEmployees(int fullTimeEmployees) {
        this.fullTimeEmployees = fullTimeEmployees;
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
}
