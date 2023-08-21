package com.softexpertpayments;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableFeignClients
public class SoftexpertPaymentsApplication {

	private static final String TIME_ZONE = "America/Sao_Paulo";

	public static void main(String[] args) {
		SpringApplication.run(SoftexpertPaymentsApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
		Locale.setDefault(new Locale("pt", "BR"));
	}

}
