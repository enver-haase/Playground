package com.example.application.data.entity;

import javax.persistence.Entity;

@Entity
public class Client {

    private String img;
    private String client;
    private double amount;
    private String status;
    private String date;
    private Integer id;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @javax.persistence.Id
    public Integer getId() {
        return id;
    }
}
