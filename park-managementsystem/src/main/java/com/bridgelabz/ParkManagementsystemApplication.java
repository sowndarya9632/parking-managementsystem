package com.bridgelabz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class ParkManagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkManagementsystemApplication.class, args);
		log.trace("trace message");
		log.debug("debug message");
		log.info("info message");
		log.warn("warn message");
		log.error("error message");
	}
}
