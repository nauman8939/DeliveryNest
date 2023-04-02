package com.example.deliverynest;

public class Complaint_POJO {

    String subject;
    String status;
    String date;
    int symbol;

    String description,resolution;
    public Complaint_POJO(String subject, String status, String date, int symbol,String description,String resolution) {
        this.subject = subject;
        this.status = status;
        this.date = date;
        this.symbol = symbol;
        this.description=description;
        this.resolution=resolution;
    }
    public String getSubject() {
        return subject;
    }
    public String getStatus() {
        return status;
    }
    public String getDate() {
        return date;
    }
    public int getSymbol() {
        return symbol;
    }
    public String getDescription() {
        return description;
    }
    public String getResolution() {
        return resolution;
    }
}
