package Entities;

import java.util.Date;

public class History {
    private Date dateHistory;
    private float open;
    private float close;
    private float adjustedClose;
    private int volume;

    public History(Date dateHistory, float open, float close, float adjustedClose, int volume) {
        this.dateHistory = dateHistory;
        this.open = open;
        this.close = close;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
    }

    public Date getDateHistory() {
        return dateHistory;
    }

    public void setDateHistory(Date dateHistory) {
        this.dateHistory = dateHistory;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getAdjustedClose() {
        return adjustedClose;
    }

    public void setAdjustedClose(float adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "History{" +
                "dateHistory=" + dateHistory +
                ", open=" + open +
                ", close=" + close +
                ", adjustedClose=" + adjustedClose +
                ", volume=" + volume +
                '}';
    }
}
