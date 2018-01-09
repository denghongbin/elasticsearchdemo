package com.starsmobi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ElasticsearchdemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchdemoApplication.class, args);
	}

}
