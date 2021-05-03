package com.zup.microservice.metrics;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.stereotype.Component;

@Component
public class ExternalServicesHealthContributor implements CompositeHealthContributor {

	private Map<String, HealthContributor> contributors = new LinkedHashMap<>();
	
	public ExternalServicesHealthContributor(AccountsApiConnectionIndicator accountsApiIndicator,
			FinancialApiConnectionIndicator financialApiIndicator, DatabaseConnectionIndicator databaseIndicator) {
		contributors.put("accounts", accountsApiIndicator);
		contributors.put("financial", financialApiIndicator);
		contributors.put("database", databaseIndicator);
	}

	@Override
	public HealthContributor getContributor(String name) {
		return contributors.get(name);
	}

	@Override
	public Iterator<NamedContributor<HealthContributor>> iterator() {
		return contributors.entrySet().stream()
				.map((entry) -> NamedContributor.of(entry.getKey(), entry.getValue()))
				.iterator();
	}

}
