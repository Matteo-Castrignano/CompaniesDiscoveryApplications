package Entities;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Report {

    private Date dateReport;
    private String typeReport;
    private String analizedValues;
    private String details;
    private String symbol;
    private String username;

    public Report(Date dateReport, String typeReport, String analizedValues, String details, String symbol, String username) {
        this.dateReport = dateReport;
        this.typeReport = typeReport;
        this.analizedValues = analizedValues;
        this.details = details;
        this.symbol = symbol;
        this.username = username;
    }

    public Date getDateReport() {
        return dateReport;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }

    public String getAnalizedValues() {
        return analizedValues;
    }

    public void setAnalizedValues(String analizedValues) {
        this.analizedValues = analizedValues;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
