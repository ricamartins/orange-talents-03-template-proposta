package com.zup.microservice.metrics;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AccountsApiConnectionIndicator implements HealthIndicator, HealthContributor {

	private final URL url;
	
	public AccountsApiConnectionIndicator(@Value("${accounts.url}") String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	@Override
	public Health health() {
		try {
			Socket socket = new Socket(url.getHost(), url.getPort());
			return Health.up().build();
		} catch (IOException e) {
			return Health.down().withDetail("error", e.getMessage()).build();
		}
	}

}
