package Entities;

public class Quote {

    private float price;
    private int dailyVolume;
    private float avgVolume;
    private float dailyOpen;
    private float dailyClose;

    public Quote(float price, int dailyVolume, float avgVolume, float dailyOpen, float dailyClose) {
        this.price = price;
        this.dailyVolume = dailyVolume;
        this.avgVolume = avgVolume;
        this.dailyOpen = dailyOpen;
        this.dailyClose = dailyClose;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDailyVolume() {
        return dailyVolume;
    }

    public void setDailyVolume(int dailyVolume) {
        this.dailyVolume = dailyVolume;
    }

    public float getAvgVolume() {
        return avgVolume;
    }

    public void setAvgVolume(float avgVolume) {
        this.avgVolume = avgVolume;
    }

    public float getDailyOpen() {
        return dailyOpen;
    }

    public void setDailyOpen(float dailyOpen) {
        this.dailyOpen = dailyOpen;
    }

    public float getDailyClose() {
        return dailyClose;
    }

    public void setDailyClose(float dailyClose) {
        this.dailyClose = dailyClose;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "price=" + price +
                ", dailyVolume=" + dailyVolume +
                ", avgVolume=" + avgVolume +
                ", dailyOpen=" + dailyOpen +
                ", dailyClose=" + dailyClose +
                '}';
    }
}

