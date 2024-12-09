package com.fashion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "orders")
public class Orders {

    @Id
    private String id; // MongoDB uses String for IDs by default
    private String pname;
    private String pdis;
    private String price;
    private String gender;
    private String category;
    private String email;
    private String proid; // Changed to String for consistency with MongoDB schema
    private byte[] image;
    private String paiername;
    private String dono;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String phononumber;
    private String delivery_date;
    private String delivery_status;

    // Constructors
    public Orders(String id, String pname, String pdis, String price, String gender, String category, 
                  String email, String proid, byte[] image, String paiername, String dono, String street, 
                  String city, String state, String pincode, String phononumber, String delivery_date, 
                  String delivery_status) {
        this.id = id;
        this.pname = pname;
        this.pdis = pdis;
        this.price = price;
        this.gender = gender;
        this.category = category;
        this.email = email;
        this.proid = proid;
        this.image = image;
        this.paiername = paiername;
        this.dono = dono;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.phononumber = phononumber;
        this.delivery_date = delivery_date;
        this.delivery_status = delivery_status;
    }

    public Orders() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdis() {
        return pdis;
    }

    public void setPdis(String pdis) {
        this.pdis = pdis;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPaiername() {
        return paiername;
    }

    public void setPaiername(String paiername) {
        this.paiername = paiername;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhononumber() {
        return phononumber;
    }

    public void setPhononumber(String phononumber) {
        this.phononumber = phononumber;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    // toString Method
    @Override
    public String toString() {
        return "Orders [id=" + id + ", pname=" + pname + ", pdis=" + pdis + ", price=" + price + ", gender=" + gender
                + ", category=" + category + ", email=" + email + ", proid=" + proid + ", image="
                + Arrays.toString(image) + ", paiername=" + paiername + ", dono=" + dono + ", street=" + street
                + ", city=" + city + ", state=" + state + ", pincode=" + pincode + ", phononumber=" + phononumber
                + ", delivery_date=" + delivery_date + ", delivery_status=" + delivery_status + "]";
    }
}
