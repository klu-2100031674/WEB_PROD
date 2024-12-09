package com.fashion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "cart")
public class Cart {

    @Id
    private String id; // MongoDB uses String for IDs by default
    private String name;
    private String dis;
    private String price;
    private String gender;
    private String category;
    private String email;
    private String cartid; // Use String for consistency with MongoDB's document structure
    private byte[] image;

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

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
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

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    // Constructor with fields
    public Cart(String id, String name, String dis, String price, String gender, String category, 
                String email, String cartid, byte[] image) {
        this.id = id;
        this.name = name;
        this.dis = dis;
        this.price = price;
        this.gender = gender;
        this.category = category;
        this.email = email;
        this.cartid = cartid;
        this.image = image;
    }

    // Default constructor
    public Cart() {
    }

    // toString Method
    @Override
    public String toString() {
        return "Cart [id=" + id + ", name=" + name + ", dis=" + dis + ", price=" + price + 
               ", gender=" + gender + ", category=" + category + ", email=" + email + 
               ", cartid=" + cartid + ", image=" + Arrays.toString(image) + "]";
    }
}
