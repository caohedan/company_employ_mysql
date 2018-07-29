package com.oocl.jpamysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.sql.DataSource;

@SpringBootApplication
public class JpaMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaMysqlApplication.class, args);
	}
}
