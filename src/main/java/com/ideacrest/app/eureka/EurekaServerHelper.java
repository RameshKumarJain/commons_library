package com.ideacrest.app.eureka;

import javax.ws.rs.core.Response;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ideacrest.app.bean.EurekaGetClientInstanceResponse;
import com.ideacrest.app.bean.RegisterEurekaInstance;
import com.ideacrest.app.configuration.ConfigurationProperty;
import com.ideacrest.app.exception.EurekaServiceException;
import com.ideacrest.app.hystrix.ClientServiceHandler;

import io.dropwizard.lifecycle.Managed;

@Singleton
public class EurekaServerHelper implements Managed {

	private Logger LOGGER = LoggerFactory.getLogger(EurekaServerHelper.class);

	private HttpClient httpClient;

	private ObjectMapper objectMapper;

	@Inject
	public EurekaServerHelper(HttpClient httpClient, ObjectMapper objectMapper) {
		this.httpClient = httpClient;
		this.objectMapper = objectMapper;
	}

	public void registerEurekaClient(RegisterEurekaInstance instance) throws EurekaServiceException {
		Response result = null;
		try {
			String value = objectMapper.writeValueAsString(instance);

			StringEntity entity = new StringEntity(value, "UTF-8");

			HttpPost httpRequest = new HttpPost(ConfigurationProperty.EUREKA_SERVER_URL_PROPERTY.get() + "apps/"
					+ ConfigurationProperty.APP_NAME_PORPERTY.get());

			httpRequest.setEntity(entity);
			httpRequest.addHeader("Content-Type", "application/json");

			ClientServiceHandler eurekaClientService = new ClientServiceHandler(httpRequest, httpClient);
			eurekaClientService.setCacheKey(instance.getInstance().getApp());

			result = eurekaClientService.execute();
		} catch (Exception e) {
		} finally {
			if (result == null || result.getStatus() != 204) {
				String msg = "Unable to register to eureka server : "
						+ ConfigurationProperty.EUREKA_SERVER_URL_PROPERTY.get();
				LOGGER.debug(msg);
				throw new EurekaServiceException(msg);
			}
		}

	}

	public String getEurekaClientInstanceUsingAppName(String appName) throws EurekaServiceException {
		try {
			HttpUriRequest httpRequest = new HttpGet(
					ConfigurationProperty.EUREKA_SERVER_URL_PROPERTY.get() + "apps/" + appName);

			httpRequest.addHeader("Content-Type", "application/json");
			httpRequest.addHeader("Accept", "application/json");

			ClientServiceHandler eurekaClientService = new ClientServiceHandler(httpRequest, httpClient);
			eurekaClientService.setCacheKey(appName);

			Response result = eurekaClientService.execute();

			EurekaGetClientInstanceResponse response = objectMapper.readValue(result.getEntity().toString(),
					EurekaGetClientInstanceResponse.class);

			return response.getApplication().getInstance().get(0).getHomePageUrl();

		} catch (Exception e) {
			String msg = "Unable to communicate to eureka server : "
					+ ConfigurationProperty.EUREKA_SERVER_URL_PROPERTY.get();
			throw new EurekaServiceException(msg);
		}
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {
		String appName = ConfigurationProperty.APP_NAME_PORPERTY.get();
		String instanceId = ConfigurationProperty.HOST_NAME_PROPERTY.get();
		try {
			HttpUriRequest httpRequest = new HttpDelete(

					ConfigurationProperty.EUREKA_SERVER_URL_PROPERTY.get() + "apps/" + appName + "/" + instanceId);
			httpRequest.addHeader("Content-Type", "application/json");
			httpRequest.addHeader("Accept", "application/json");

			ClientServiceHandler eurekaClientService = new ClientServiceHandler(httpRequest, httpClient);
			eurekaClientService.setCacheKey(appName);
			eurekaClientService.execute();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
	}
}
