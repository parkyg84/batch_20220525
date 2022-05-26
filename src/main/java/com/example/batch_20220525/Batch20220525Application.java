package com.example.batch_20220525;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Batch20220525Application {

	public static void main(String[] args) {
		SpringApplication.run(Batch20220525Application.class, args);
	}

}
