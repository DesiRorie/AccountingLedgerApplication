package com.pluralsight;


import java.text.DecimalFormat;

public class Deposit {
    private  String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    public String getDate() {
        return date;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Deposit(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    @Override
    public String toString() {
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return  date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }
}
