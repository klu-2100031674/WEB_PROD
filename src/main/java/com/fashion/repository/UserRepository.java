package com.fashion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.fashion.model.Users;

// Define the repository interface
public interface UserRepository extends MongoRepository<Users,String> {

    // Method to find a user by email and password
    public Users findByEmailAndPassword(String email, String password);
}
