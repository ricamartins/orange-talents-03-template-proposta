package com.zup.microservice.proposal.apis;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;

import com.zup.microservice.proposal.apis.FinancialAnalysisApi.FinancialAnalysisConfiguration;
import com.zup.microservice.proposal.dto.FinancialAnalysisSolicitation;

import feign.Client;
import feign.Request;
import feign.Response;
import feign.Request.Options;

@FeignClient(value="financial", url="${financial.url}", configuration=FinancialAnalysisConfiguration.class)
public interface FinancialAnalysisApi {

	@PostMapping("/solicitacao")
	FinancialAnalysisSolicitation analyse(FinancialAnalysisSolicitation solicitation);
	
	class FinancialAnalysisConfiguration {
		
		public FinancialAnalysisConfiguration() {}
		
		@Bean
		public Client client() {
			return new FinancialAnalysisClient(null, null);
		}
	}
	
	class FinancialAnalysisClient extends Client.Default {

		public FinancialAnalysisClient(SSLSocketFactory sslContextFactory, HostnameVerifier hostnameVerifier) {
			super(sslContextFactory, hostnameVerifier);
		}
		
		@Override
		public Response execute(Request request, Options options) throws IOException {
		
			Response response = super.execute(request, options);
			
			if (response.status() == HttpStatus.UNPROCESSABLE_ENTITY.value())
				return response.toBuilder().status(HttpStatus.OK.value()).build();
			
			return response;
		}

	}
}
