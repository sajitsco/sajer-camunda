package com.sajits.sajer.samo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SamoApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SamoApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SamoApp.class);
	}

	@CrossOrigin("*")
	@RestController
	@RequestMapping("/")
	public class SajerAPI {
		@GetMapping("/")
		String index() {
			return "samo index page";
		}

	}

}