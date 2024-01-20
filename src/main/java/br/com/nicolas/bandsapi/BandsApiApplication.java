package br.com.nicolas.bandsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BandsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BandsApiApplication.class, args);
	}

}
