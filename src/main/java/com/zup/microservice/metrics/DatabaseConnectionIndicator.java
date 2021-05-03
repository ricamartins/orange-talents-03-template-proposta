package com.zup.microservice.metrics;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionIndicator implements HealthIndicator, HealthContributor {

	private DataSource ds;
	
	public DatabaseConnectionIndicator(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public Health health() {
		try {
			Connection connection = ds.getConnection();
			Statement statement = connection.createStatement();
			statement.execute("select 1 from tb_proposals");
			return Health.up().build();
		} catch (SQLException e) {
			return Health.outOfService().withException(e).build();
		}
	}

}
