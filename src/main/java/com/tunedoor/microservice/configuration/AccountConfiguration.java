package com.tunedoor.microservice.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * 
 * @author Mohamed Saeed
 *
 */
@Configuration
@ComponentScan
@EntityScan("com.tunedoor.microservice.model")
@EnableJpaRepositories("com.tunedoor.microservice.repository")
@PropertySource("classpath:db-config.properties")
public class AccountConfiguration {

	/**
	 * Create data source bean
	 * @return datasource
	 */
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:db/schema.sql")
				.addScript("classpath:db/seed-data.sql").build();
		return dataSource;
	}
}
