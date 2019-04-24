package com.ideacrest.app.eureka;

import java.io.IOException;
import java.util.Arrays;

import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideacrest.app.bean.EurekaClientApplicationResponse;
import com.ideacrest.app.bean.EurekaGetClientInstanceResponse;
import com.ideacrest.app.bean.EurekaInstance;
import com.ideacrest.app.bean.RegisterEurekaInstance;
import com.ideacrest.app.exception.EurekaServiceException;

public class EurekaServerHelperTest {

	@InjectMocks
	private EurekaServerHelper eurekaServerHelper;

	@Mock
	private HttpClient httpClient;

	@Mock
	private ObjectMapper objectMapper;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRegisterEurekaClient() throws Exception {
		RegisterEurekaInstance instance = getEurekaRegisterMockInstance();
		HttpResponse mockResponse = getHttpResponseMockData();
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(mockResponse);

		Mockito.when(objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"dummy\" :\"Result\" }");

		eurekaServerHelper.registerEurekaClient(instance);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testRegisterEurekaClientWithInvalidAppName() throws Exception {
		RegisterEurekaInstance instance = getEurekaRegisterMockInstance();
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenThrow(new RuntimeException("Timeout"));

		Mockito.when(objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"dummy\" :\"Result\" }");

		thrown.expect(EurekaServiceException.class);
		thrown.expectMessage("Unable to register to eureka server : http://localhost:8080");
		eurekaServerHelper.registerEurekaClient(instance);
	}

	@Rule
	public ExpectedException thrownException = ExpectedException.none();

	@Test
	public void testGetEurekaClientInstanceUsingInvalidAppName() throws ClientProtocolException, IOException {
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenThrow(new RuntimeException("Timeout"));

		thrownException.expect(EurekaServiceException.class);
		thrownException.expectMessage("Unable to communicate to eureka server : http://localhost:8080");
		eurekaServerHelper.getEurekaClientInstanceUsingAppName("TestAppName");
	}

	@Test
	public void testGetEurekaClientInstanceUsingAppName() throws JsonParseException, JsonMappingException, IOException {
		String expectedData = "http://localhost:9100";
		EurekaGetClientInstanceResponse response = getEurekaClientInstanceResponse(expectedData);

		HttpResponse mockResponse = getHttpResponseMockData();
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(mockResponse);

		Mockito.when(objectMapper.readValue(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(response);

		String result = eurekaServerHelper.getEurekaClientInstanceUsingAppName("TestAppName");

		Assert.assertTrue("Invalid response.\nExpected: " + expectedData + " but found : " + result,
				expectedData.equals(result));

	}

	@Rule
	public ExpectedException thrownEurekaException = ExpectedException.none();

	@Test
	public void testGetEurekaClientInstanceUsingAppNameWithNullResponse()
			throws JsonParseException, JsonMappingException, IOException {
		HttpResponse mockResponse = getHttpResponseMockData();
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(mockResponse);

		Mockito.when(objectMapper.readValue(Mockito.anyString(), Mockito.any(Class.class)))
				.thenReturn(new EurekaGetClientInstanceResponse());

		thrownException.expect(EurekaServiceException.class);
		thrownException.expectMessage("Unable to communicate to eureka server : http://localhost:8080");
		eurekaServerHelper.getEurekaClientInstanceUsingAppName("TestAppName");

	}

	@Test
	public void testStartMethod() {
		eurekaServerHelper.start();
	}

	@Test
	public void testStopMethodWithValidData() throws Exception {
		HttpResponse mockResponse = getHttpResponseMockData();
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(mockResponse);
		eurekaServerHelper.stop();
	}

	@Test
	public void testStopMethodWithExceptionFromHttpClient() throws Exception {
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenThrow(new RuntimeException("Timeout"));
		eurekaServerHelper.stop();
	}

	private EurekaGetClientInstanceResponse getEurekaClientInstanceResponse(String expectedData) {
		EurekaGetClientInstanceResponse response = new EurekaGetClientInstanceResponse();
		response.setApplication(new EurekaClientApplicationResponse());
		response.getApplication().setInstance(Arrays.asList(new EurekaInstance()));
		response.getApplication().getInstance().get(0).setHomePageUrl(expectedData);
		return response;
	}

	private HttpResponse getHttpResponseMockData() {
		ProtocolVersion ver = new ProtocolVersion("http", 1, 1);
		int expectedStatus = Status.NO_CONTENT.getStatusCode();
		HttpResponse mockResponse = new BasicHttpResponse(ver, expectedStatus, Status.OK.getReasonPhrase());
		return mockResponse;
	}

	private RegisterEurekaInstance getEurekaRegisterMockInstance() {
		RegisterEurekaInstance instance = new RegisterEurekaInstance();
		instance.setInstance(new EurekaInstance());
		instance.getInstance().setApp("TestApp");
		return instance;
	}
}
