package com.harmonycare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HarmonycareApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarmonycareApplication.class, args);
	}
}
