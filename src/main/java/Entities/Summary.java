package Entities;

import java.util.Date;

public class Summary {
    private String symbol;
    private String date;
    private float marketCap;
    private float peRatio;
    private float EPS;
    private String dividend;
    private float targetPrice;
    private float open;
    private float close;
    private float avgVolume;
    private float volume;

    public Summary(){

    }
    public Summary(String symbol, String date, float marketCap, float peRatio, float EPS, String dividend, float targetPrice, float open, float close, float avgVolume, float volume) {
        this.symbol = symbol;
        this.date = date;
        this.marketCap = marketCap;
        this.peRatio = peRatio;
        this.EPS = EPS;
        this.dividend = dividend;
        this.targetPrice = targetPrice;
        this.open = open;
        this.close = close;
        this.avgVolume = avgVolume;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDate() {
        return date;
    }

    public float getMarketCap() {
        return marketCap;
    }

    public float getPeRatio() {
        return peRatio;
    }

    public float getEPS() {
        return EPS;
    }

    public String getDividend() {
        return dividend;
    }

    public float getTargetPrice() {
        return targetPrice;
    }

    public float getOpen() {
        return open;
    }

    public float getClose() {
        return close;
    }

    public float getAvgVolume() {
        return avgVolume;
    }

    public float getVolume() {
        return volume;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMarketCap(float marketCap) {
        this.marketCap = marketCap;
    }

    public void setPeRatio(float peRatio) {
        this.peRatio = peRatio;
    }

    public void setEPS(float EPS) {
        this.EPS = EPS;
    }

    public void setDividend(String dividend) {
        this.dividend = dividend;
    }

    public void setTargetPrice(float targetPrice) {
        this.targetPrice = targetPrice;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setAvgVolume(float avgVolume) {
        this.avgVolume = avgVolume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}