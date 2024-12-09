package com.fashion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.fashion.model.Cart;
import com.fashion.repository.CartRepository;
@Service
public class UsersCartServiceImp implements UsersCartService {
	
	@Autowired
	private CartRepository cartrepo;
	

	public String adduser(Cart ur) {
	
		cartrepo.save(ur);
		return "added Successfully";
		
	}
	@Override
	public void deleteCartItem(String itemId) {
	    cartrepo.deleteById(itemId); // itemId is a String, which matches the type in the Cart model
	}

	@Override
	public String updateuser(Cart updatedUser) {
	 return null;
	}


	@Override
	public Cart viewuserbyid(String eid) 
	{
		Optional<Cart> obj = cartrepo.findById(eid);    
	    
	    if(obj.isPresent())
	    {
	      Cart emp = obj.get();
	      return emp;
	    }
	    else
	    {
	      return null;
	    }
	}
	 public List<Cart> getCartItemsByEmail(String email) {
	        return cartrepo.findAllByEmail(email);
	    }
	    




}
