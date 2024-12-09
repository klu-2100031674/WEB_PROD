package com.fashion.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.fashion.model.Cart;
public interface CartRepository extends MongoRepository<Cart	, String>{
	List<Cart> findAllByEmail(String email);

}
