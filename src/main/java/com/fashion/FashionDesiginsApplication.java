package com.fashion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RestController
public class FashionDesiginsApplication {
	@GetMapping("/hi)
	public String example(){
		return "hi";
	}
	public static void main(String[] args) {
		SpringApplication.run(FashionDesiginsApplication.class, args);
	}

}
