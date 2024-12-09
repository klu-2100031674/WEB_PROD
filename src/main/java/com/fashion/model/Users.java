package com.fashion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Users {

    @Id
    private String id; // MongoDB typically uses String as the ID type
    
    private String name;
    private String email;
    private long number;
    private String password;
    private boolean active;
    private String addressLine1;
    private String addressLine2;
    private String postcode;
    private String state;
    private String city;
    private String country;
    private String admin;

    // Default Constructor
    public Users() {}

    // Constructor with parameters
    public Users(String id, String name, String email, long number, String password, boolean active, 
                 String addressLine1, String addressLine2, String postcode, String state, 
                 String city, String country, String admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.password = password;
        this.active = active;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postcode = postcode;
        this.state = state;
        this.city = city;
        this.country = country;
        this.admin = admin;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", name=" + name + ", email=" + email + ", number=" + number + ", password="
                + password + ", active=" + active + ", addressLine1=" + addressLine1 + ", addressLine2="
                + addressLine2 + ", postcode=" + postcode + ", state=" + state + ", city=" + city + ", country="
                + country + ", admin=" + admin + "]";
    }
}
