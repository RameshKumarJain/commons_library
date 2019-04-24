package com.ideacrest.app.hystrix;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideacrest.app.configuration.ConfigurationProperty;
import com.ideacrest.app.exception.HystrixServiceException;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class ClientServiceHandler extends HystrixCommand<Response> {

	private Logger LOGGER = LoggerFactory.getLogger(ClientServiceHandler.class);

	private HttpUriRequest httpRequest;

	private HttpClient httpClient;

	private String cacheKey;

	HystrixRequestContext context = HystrixRequestContext.initializeContext();

	public ClientServiceHandler(HttpUriRequest httpRequest, HttpClient httpClient) {

		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DefaultGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("app_cache"))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000000000))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(
								ConfigurationProperty.CIRCUIT_ERROR_THRESHOLD_PERCENTAGE_PROPERTY.get()))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter().withCircuitBreakerRequestVolumeThreshold(
								ConfigurationProperty.CIRCUIT_REQUEST_VOLUME_THRESHOLD_PROPERTY.get())));
		this.httpRequest = httpRequest;
		this.httpClient = httpClient;
	}

	@Override
	public Response execute() {
		return super.execute();
	}

	@Override
	public Response run() throws HystrixServiceException {
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpRequest);
			Response response = Response.status(httpResponse.getStatusLine().getStatusCode())
					.entity(getResponseString(httpResponse)).build();
			return response;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new HystrixServiceException(e.getMessage(), e);
		}
	}

	@Override
	protected String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	private String getResponseString(HttpResponse response) throws Exception {
		if (response.getEntity() != null && response.getEntity().getContent() != null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		}
		return "";
	}
}
