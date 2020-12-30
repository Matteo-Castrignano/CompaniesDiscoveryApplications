package Entities;

public class Stats {
    private int capitalization;
    private float PE_ratio;
    private float EPS;
    private float dividend;
    private float targetPrice;

    public Stats(int capitalization, float PE_ratio, float EPS, float dividend, float targetPrice) {
        this.capitalization = capitalization;
        this.PE_ratio = PE_ratio;
        this.EPS = EPS;
        this.dividend = dividend;
        this.targetPrice = targetPrice;
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

    @Override
    public String toString() {
        return "Stats{" +
                "capitalization=" + capitalization +
                ", PE_ratio=" + PE_ratio +
                ", EPS=" + EPS +
                ", dividend=" + dividend +
                ", targetPrice=" + targetPrice +
                '}';
    }
}
