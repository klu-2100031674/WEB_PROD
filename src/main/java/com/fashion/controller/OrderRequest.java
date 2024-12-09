package com.fashion.controller;

public class OrderRequest {
    private int amount;
    private String info;
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public OrderRequest(int amount, String info) {
		super();
		this.amount = amount;
		this.info = info;
	}

    // Getters and setters
}