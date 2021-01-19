package Entities;

public class Summary {
    private int capitalization;
    private float PE_ratio;
    private float EPS;
    private float dividend;
    private float targetPrice;
    private float open;
    private float close;
    private float avgVolume;
    private float volume;

    public Summary(int capitalization, float PE_ratio, float EPS, float dividend, float targetPrice, float open, float close, float avgVolume, float volume) {
        this.capitalization = capitalization;
        this.PE_ratio = PE_ratio;
        this.EPS = EPS;
        this.dividend = dividend;
        this.targetPrice = targetPrice;
        this.open = open;
        this.close = close;
        this.avgVolume = avgVolume;
        this.volume = volume;
    }

    public int getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(int capitalization) {
        this.capitalization = capitalization;
    }

    public float getPE_ratio() {
        return PE_ratio;
    }

    public void setPE_ratio(float PE_ratio) {
        this.PE_ratio = PE_ratio;
    }

    public float getEPS() {
        return EPS;
    }

    public void setEPS(float EPS) {
        this.EPS = EPS;
    }

    public float getDividend() {
        return dividend;
    }

    public void setDividend(float dividend) {
        this.dividend = dividend;
    }

    public float getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(float targetPrice) {
        this.targetPrice = targetPrice;
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

    public float getAvgVolume() {
        return avgVolume;
    }

    public void setAvgVolume(float avgVolume) {
        this.avgVolume = avgVolume;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
