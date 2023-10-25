package com.pluralsight;


import java.text.DecimalFormat;

public class Transaction implements Comparable<Transaction> {
    private  String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

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



    public Transaction(String date, String time, String description, String vendor, double amount, String type) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.type = type;


    }
    @Override
    public int compareTo(Transaction otherTransaction) {
        // Compare transactions based on their dates
        return otherTransaction.getDate().compareTo(this.date);
    }
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedAmount = decimalFormat.format(amount);
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return  date + "|" + time + "|" + description + "|" + vendor + "|" + formattedAmount  ;
    }
}
