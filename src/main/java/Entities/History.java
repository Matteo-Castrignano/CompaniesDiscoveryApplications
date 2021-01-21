package Entities;

import java.util.Date;

public class History {
    private String symbol;
    private String dateHistory;
    private float open;
    private float close;
    private float adjustedClose;
    private int volume;
    private float high;
    private float low;

    public History(String symbol, String dateHistory, float open, float close, float adjustedClose, int volume, float high, float low) {
        this.symbol = symbol;
        this.dateHistory = dateHistory;
        this.open = open;
        this.close = close;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
        this.high = high;
        this.low = low;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDateHistory() {
        return dateHistory;
    }

    public void setDateHistory(String dateHistory) {
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

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }
}
