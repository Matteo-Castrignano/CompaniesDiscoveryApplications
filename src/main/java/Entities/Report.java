package Entities;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Report {

    private Date dateReport;
    private List<String> typeReport;
    private Map <String, Integer> analizedValues;
    private String details;

    public Report(Date dateReport, List<String> typeReport, Map<String, Integer> analizedValues, String details) {
        this.dateReport = dateReport;
        this.typeReport = typeReport;
        this.analizedValues = analizedValues;
        this.details = details;
    }

    public Date getDateReport() {
        return dateReport;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
    }

    public List<String> getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(List<String> typeReport) {
        this.typeReport = typeReport;
    }

    public Map<String, Integer> getAnalizedValues() {
        return analizedValues;
    }

    public void setAnalizedValues(Map<String, Integer> analizedValues) {
        this.analizedValues = analizedValues;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Report{" +
                "dateReport=" + dateReport +
                ", typeReport=" + typeReport +
                ", analizedValues=" + analizedValues +
                ", details='" + details + '\'' +
                '}';
    }
}
