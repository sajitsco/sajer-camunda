package com.sajits.sajer.acesa;

import java.util.List;

import com.sajits.sajer.acesa.archive.infrastructure.DocumentRepository;
import com.sajits.sajer.acesa.sample.Book;
import com.sajits.sajer.acesa.sample.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class App  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	@CrossOrigin("*")
	@RestController
	@RequestMapping("/")
	public class SajerAPI {
		@Autowired
    	private BookRepository bookRepository;

		@Autowired
    	private DocumentRepository documentRepository;

	
		@GetMapping("/test")
		String visit() {
			List<Book> books = bookRepository.findAll();
			String out = "";
			for (Book book : books) {
				out += book.getName() + "</br>";
			}
			return out;
		}

		@GetMapping("/document")
		ResponseEntity<?> document() {
        	return ResponseEntity.ok(documentRepository.findAll());
    	}

	}

}