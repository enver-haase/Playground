package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.application.data.AbstractEntity;

import java.math.BigDecimal;

@Entity
public class SampleAddress extends AbstractEntity {

    @Size(min = 5, max = 10, message = "between 5 and 10 characters, please!")
    private String street;

    @Pattern(regexp = "[0-5]+", message = "Du Vogel, so sieht doch keine PLZ aus.")
    private String postalCode;

    @Pattern(regexp = "[a-zA-Z0-9]+", message = "So sieht kein Name einer Stadt aus.")
    private String city;

    private String state;
    private String country;
    private BigDecimal amount;
    private BigDecimal fine;
    private String period;


    private String email;

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
    public String getPeriod(){
        return this.period;
    }
    public void setPeriod(String period){
        this.period = period;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
