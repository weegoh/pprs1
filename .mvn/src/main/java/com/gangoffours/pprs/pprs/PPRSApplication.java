package com.gangoffours.pprs.pprs;

import com.gangoffours.pprs.pprs.services.IDrugInteractivityService;
import com.gangoffours.pprs.pprs.services.IDrugVectorService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@EnableCaching
@SpringBootApplication
public class PPRSApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PPRSApplication.class, args);

		// ToDo make async
		context.getBean(IDrugVectorService.class).GetVectorsAsync();
		context.getBean(IDrugInteractivityService.class).GetInteractionsAsync();
	}

}
