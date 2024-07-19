package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
public class GtpManagemetAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtpManagemetAppBackendApplication.class, args);
		System.out.println();
	}

}
