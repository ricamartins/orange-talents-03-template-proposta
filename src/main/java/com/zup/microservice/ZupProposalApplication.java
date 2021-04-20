package com.zup.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZupProposalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZupProposalApplication.class, args);
	}

}
