package com.example.calltohome.models;


public class Order {
    private String Order_id;
    private String adode;
    private String repair_list;
    private String date;

    public Order() {

    }

    public Order(String order_id, String adode, String repair_list, String date) {
        Order_id = order_id;
        this.adode = adode;
        this.repair_list = repair_list;
        this.date = date;
    }


    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getAdode() {
        return adode;
    }

    public void setAdode(String adode) {
        this.adode = adode;
    }

    public String getRepair_list() {
        return repair_list;
    }

    public void setRepair_list(String repair_list) {
        this.repair_list = repair_list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
