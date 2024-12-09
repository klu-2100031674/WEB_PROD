package com.fashion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashion.model.Admin;
import com.fashion.model.Orders;
import com.fashion.repository.AdminRepo;
import com.fashion.repository.OrdersRepository;

@Service
public class AdminServiceImp implements AdminService{

	@Autowired
	private AdminRepo repo;
	
	@Override
	public List<Admin> getAllAdmins() {
		return repo.findAll(); 
	}
}
