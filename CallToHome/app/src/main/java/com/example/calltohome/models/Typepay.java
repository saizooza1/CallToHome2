package com.example.calltohome.models;

public class Typepay {

    private String payId;
    private String payName;

    public Typepay() {

    }

    public Typepay(String payId, String payName) {
        this.payId = payId;
        this.payName = payName;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }
}
