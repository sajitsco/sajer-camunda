package com.sajits.sajer.acesa;

import org.camunda.bpm.engine.spring.application.SpringProcessApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {
	@Bean
	public SpringProcessApplication processApplication()
	{
		return new SpringProcessApplication();
	}
}
