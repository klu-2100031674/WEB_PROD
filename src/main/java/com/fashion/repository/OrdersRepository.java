package com.fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.*;
import com.fashion.model.*;
public interface OrdersRepository extends MongoRepository<Orders	,String>{
	List<Orders> findAllByEmail(String email);

}
