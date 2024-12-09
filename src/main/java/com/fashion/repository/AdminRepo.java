package com.fashion.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fashion.model.Admin;
public interface AdminRepo extends MongoRepository<Admin	, String> {

}
