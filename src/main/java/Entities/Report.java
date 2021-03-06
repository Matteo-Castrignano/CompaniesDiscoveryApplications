package Entities;

public class Report {

    private String dateReport;
    private String typeReport;
    private String analizedValues;
    private String details;
    private String symbol;
    private String username;
    private String title;

    public Report(String title, String dateReport, String typeReport, String analizedValues, String details, String symbol, String username) {
        this.title= title;
        this.dateReport = dateReport;
        this.typeReport = typeReport;
        this.analizedValues = analizedValues;
        this.details = details;
        this.symbol = symbol;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateReport() {
        return dateReport;
    }

    public void setDateReport(String dateReport) {
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

    @Override
    public String toString() {
        return  "\n" + "title='" + title + '\'' +
                ", symbol='" + symbol + '\'' +
                ", username='" + username + '\'' +
                ", dateReport='" + dateReport + '\'' +
                ", typeReport='" + typeReport + '\'' +
                ", analizedValues='" + analizedValues + '\'' +
                ", details='" + details + '\'';


    }
}
