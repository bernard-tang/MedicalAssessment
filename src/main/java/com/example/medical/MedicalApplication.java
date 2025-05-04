package com.example.medical;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.medical.entity.Record;
import com.example.medical.respository.RecordRepository;


@SpringBootApplication
public class MedicalApplication extends SpringBootServletInitializer{
	
	private static final Logger log = LoggerFactory.getLogger(MedicalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MedicalApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(RecordRepository repository) {
		return (args) -> {	
			// save a few customers
			repository.save(new Record("asj", 22, List.of("Diabetes", "Asthma")));
			

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			repository.findAll().forEach(record -> {
				log.info(record.getName());
			});
			log.info("");

			// fetch an individual customer by ID
			Optional<Record> record = repository.findById(1L);
			log.info("Customer found with findById(1L):");
			log.info("--------------------------------");
			log.info(record.get().getName());
			log.info("");

			
		};
	}

}
