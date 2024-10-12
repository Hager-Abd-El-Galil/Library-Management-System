package com.system.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LibraryManagementSysemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSysemApplication.class, args);
	}

}