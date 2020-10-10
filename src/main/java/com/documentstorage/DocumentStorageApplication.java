package com.documentstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class DocumentStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentStorageApplication.class, args);
	}

}
