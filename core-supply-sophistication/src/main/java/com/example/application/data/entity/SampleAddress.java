package com.example.application.data.entity;

import javax.persistence.Entity;

import com.example.application.data.AbstractEntity;

import java.math.BigDecimal;

@Entity
public class SampleAddress extends AbstractEntity {

    private String street;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private BigDecimal amount;
    private BigDecimal fine;
    private String period;

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public BigDecimal getAmount(){ return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getFine() { return this.fine; }
    public void setFine(BigDecimal fine) { this.fine = fine; }
    public void setPeriod(String period){
        this.period = period;
    }
    public String getPeriod(){
        return this.period;
    }
}
