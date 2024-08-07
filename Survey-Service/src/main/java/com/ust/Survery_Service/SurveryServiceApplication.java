package com.ust.Survery_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SurveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveryServiceApplication.class, args);
	}

}
