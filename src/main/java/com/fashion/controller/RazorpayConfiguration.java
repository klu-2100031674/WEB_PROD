package com.fashion.controller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Configuration
public class RazorpayConfiguration {
    @Bean
    public RazorpayClient razorpayClient() {
        try {
            // Provide your actual API key and secret here
            return new RazorpayClient("your_api_key", "your_api_secret");
        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to initialize RazorpayClient", e);
        }
    }
}
