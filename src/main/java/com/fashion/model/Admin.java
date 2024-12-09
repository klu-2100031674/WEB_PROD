package com.fashion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin")
public class Admin {
    @Id
    private String id; // MongoDB uses String for IDs by default
    private String name;
    private String adminRating;

    // Parameterized Constructor
    public Admin(String id, String name, String adminRating) {
        this.id = id;
        this.name = name;
        this.adminRating = adminRating;
    }

    // Default Constructor
    public Admin() {
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

    public String getAdminRating() {
        return adminRating;
    }

    public void setAdminRating(String adminRating) {
        this.adminRating = adminRating;
    }

    // toString Method
    @Override
    public String toString() {
        return "Admin [id=" + id + ", name=" + name + ", adminRating=" + adminRating + "]";
    }
}
