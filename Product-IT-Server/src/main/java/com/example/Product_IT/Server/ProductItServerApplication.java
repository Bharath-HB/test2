package com.example.Product_IT.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ProductItServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductItServerApplication.class, args);
	}

}
