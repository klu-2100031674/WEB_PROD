package com.fashion.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RazorpayService {
    @Autowired
    private RazorpayClient client;

    public String createOrder(String amount) throws RazorpayException {
        try {
            double amountValue = Double.parseDouble(amount);
            long amountInPaise = (long)(amountValue * 100); // Convert to paise (smallest currency unit)

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise); // amount in paise
            orderRequest.put("currency", "INR"); // currency code (change to the desired currency)
            orderRequest.put("receipt", "order_rcptid_11"); // your order receipt ID

            Order order = client.Orders.create(orderRequest);

            return order.get("id");
        } catch (RazorpayException e) {
            // Handle RazorpayException
            System.out.println(e.getMessage());
            return null;
        }
    }
}
