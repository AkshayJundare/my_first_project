package com.example.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages={"com.example.crud"})
@EnableCaching
@EnableJpaRepositories(basePackages = {"com.example.crud.repository"}) // scans repositories
@EntityScan(basePackages = {"com.example.crud.entity"})  


public class SpringbootCrudJpaApplication {

	public static void main(String[] args) {
		System.out.println("Welcome");
		SpringApplication.run(SpringbootCrudJpaApplication.class, args);
		System.out.println("Welcome ho,me");
	}

}
