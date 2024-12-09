package com.fashion.service;

import java.util.List;

import com.fashion.model.Cart;
import com.fashion.model.Products;
public interface UsersCartService
{
	public  String adduser(Cart emp);
	public String updateuser(Cart emp);
	public Cart viewuserbyid(String eid);
	List<Cart>getCartItemsByEmail(String email);
	void deleteCartItem(String string);
	
}
