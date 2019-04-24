package com.ideacrest.app.hystrix;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
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

import com.netflix.hystrix.exception.HystrixRuntimeException;

public class ClientServiceHandlerTest {

	@InjectMocks
	ClientServiceHandler clientServiceHandler;

	@Mock
	private HttpUriRequest httpRequest;

	@Mock
	private HttpClient httpClient;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		clientServiceHandler.setCacheKey("sample-test");
	}

	@Test
	public void testPositiveResponseExecute() throws ClientProtocolException, IOException {
		ProtocolVersion ver = new ProtocolVersion("http", 1, 1);
		int expectedStatus = Status.OK.getStatusCode();
		String responseMessage = "Got the response data";
		HttpResponse mockResponse = new BasicHttpResponse(ver, expectedStatus, Status.OK.getReasonPhrase());
		mockResponse.setEntity(new StringEntity(responseMessage));
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(mockResponse);
		Response actualResponse = clientServiceHandler.execute();
		Assert.assertTrue("Status code should be 200", Status.OK.getStatusCode() == actualResponse.getStatus());
		Assert.assertTrue("Wrong message", responseMessage.equalsIgnoreCase(actualResponse.getEntity().toString()));
	}

	@Test
	public void testPositiveResponseWithEmptyMessageExecute() throws ClientProtocolException, IOException {
		ProtocolVersion ver = new ProtocolVersion("http", 1, 1);
		int expectedStatus = Status.NO_CONTENT.getStatusCode();
		HttpResponse mockResponse = new BasicHttpResponse(ver, expectedStatus, Status.NO_CONTENT.getReasonPhrase());
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(mockResponse);
		Response actualResponse = clientServiceHandler.execute();
		Assert.assertTrue("Status code should be 200", Status.NO_CONTENT.getStatusCode() == actualResponse.getStatus());
		Assert.assertTrue("response message should be empty",
				actualResponse.getEntity() != null && actualResponse.getEntity().toString().isEmpty());
	}

	@Test
	public void testInterserverResponseExecution() throws ClientProtocolException, IOException {
		ProtocolVersion ver = new ProtocolVersion("http", 1, 1);
		int expectedStatus = Status.INTERNAL_SERVER_ERROR.getStatusCode();
		String responseMessage = "Got the response data as error";
		HttpResponse mockResponse = new BasicHttpResponse(ver, expectedStatus,
				Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
		mockResponse.setEntity(new StringEntity(responseMessage));
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(mockResponse);
		Response actualResponse = clientServiceHandler.execute();
		Assert.assertTrue("Status code should be 200",
				Status.INTERNAL_SERVER_ERROR.getStatusCode() == actualResponse.getStatus());
		Assert.assertTrue("Wrong message", responseMessage.equalsIgnoreCase(actualResponse.getEntity().toString()));
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testHystrixRuntimeExceptionOnExcetue() throws ClientProtocolException, IOException {
		thrown.expect(HystrixRuntimeException.class);
		thrown.expectMessage("app_cache failed and no fallback available.");
		Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenThrow(new RuntimeException("Timeout"));
		clientServiceHandler.execute();
	}

}
