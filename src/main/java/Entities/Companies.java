package Entities;

import java.util.List;

public class Companies {

    private String Symbol;
    private String Name;
    private String Exchange;
    private String Sector;
    private int FullTimesEmployees;
    private String Description;
    private String City;
    private String Phone;
    private String State;
    private String Country;
    private String Address;
    private String Website;
    private List<Summary> Summary;

    public Companies(String Symbol, String Name, String stockExchange, String Sector, int fullTimeEmployees, String Description, String City, String Phone, String State, String Country, String Address, String Website) {
        this.Symbol = Symbol;
        this.Name = Name;
        this.Exchange = stockExchange;
        this.Sector = Sector;
        this.FullTimesEmployees = fullTimeEmployees;
        this.Description = Description;
        this.City = City;
        this.Phone = Phone;
        this.State = State;
        this.Country = Country;
        this.Address = Address;
        this.Website = Website;
    }

    public Companies(String Symbol, String Name, String stockExchange, String Sector) {
        this.Symbol = Symbol;
        this.Name = Name;
        this.Exchange = stockExchange;
        this.Sector = Sector;
    }

    public Companies() {
        this.Symbol = null;
        this.Name = null;
        this.Exchange = null;
        this.Sector = null;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getStockExchange() {
        return Exchange;
    }

    public void setStockExchange(String stockExchange) {
        this.Exchange = stockExchange;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String Sector) {
        this.Sector = Sector;
    }

    public int getFullTimeEmployees() {
        return FullTimesEmployees;
    }

    public void setFullTimeEmployees(int fullTimeEmployees) {
        this.FullTimesEmployees = fullTimeEmployees;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }

    public List<Summary> getSummary() {
        return Summary;
    }

    public void setSummary(List<Summary> Summary) {
        this.Summary = Summary;
    }

    @Override
    public String toString() {
        return "Companies{" +
                "Symbol='" + Symbol + '\'' +
                ", Name='" + Name + '\'' +
                ", Exchange='" + Exchange + '\'' +
                ", Sector='" + Sector + '\'' +
                ", FullTimesEmployees=" + FullTimesEmployees +
                ", Description='" + Description + '\'' +
                ", City='" + City + '\'' +
                ", Phone='" + Phone + '\'' +
                ", State='" + State + '\'' +
                ", Country='" + Country + '\'' +
                ", Address='" + Address + '\'' +
                ", Website='" + Website + '\'' +
                '}';
    }
}
