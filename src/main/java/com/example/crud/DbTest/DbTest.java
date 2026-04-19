package com.example.crud.DbTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DbTest implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Datasource = " + dataSource);
		System.out.println("Connection = " + dataSource.getConnection());
	}
}
