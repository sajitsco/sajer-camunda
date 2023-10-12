package com.sajits.sajer.acesa.sample;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("pr1t2")
public class Pr1T2  implements JavaDelegate {
      @Autowired
    	private BookRepository bookRepository;
      
    public void execute(DelegateExecution delegate) {
        System.out.println("pr1t2");
        List<Book> books = bookRepository.findAll();
			String out = "";
			for (Book book : books) {
				out += book.getName() + "</br>";
			}

      
			System.out.println(out);
      }
}

