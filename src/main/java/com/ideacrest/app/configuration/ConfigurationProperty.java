package com.ideacrest.app.configuration;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

public class ConfigurationProperty {

	public static final String EXECUTION_TIMEOUT = "EXECUTION_TIMEOUT";

	public static final String CIRCUIT_ERROR_THRESHOLD_PERCENTAGE = "CIRCUIT_ERROR_THRESHOLD_PERCENTAGE";

	public static final String CIRCUIT_REQUEST_VOLUME_THRESHOLD = "CIRCUIT_REQUEST_VOLUME_THRESHOLD";

	public static final String EUREKA_SERVER_URL = "EUREKA_SERVER_URL";

	public static final String HOST_NAME = "HOST_NAME";

	public static final String APP_NAME = "APP_NAME";

	public static final String IP_ADDRESS = "IP_ADDRESS";

	public static final String PORT = "PORT";

	public static final String HOST_URL = "HOST_URL";

	public static final String MONGO_HOST = "mongoHost";

	public static final String MONGO_PORT = "mongoPort";

	public static final String MONGO_DB = "mongoDB";

	public static DynamicIntProperty EXECUTION_TIMEOUT_PROPERTY = DynamicPropertyFactory.getInstance()
			.getIntProperty(EXECUTION_TIMEOUT, 2000);

	public static DynamicIntProperty CIRCUIT_ERROR_THRESHOLD_PERCENTAGE_PROPERTY = DynamicPropertyFactory.getInstance()
			.getIntProperty(CIRCUIT_ERROR_THRESHOLD_PERCENTAGE, 50);

	public static DynamicIntProperty CIRCUIT_REQUEST_VOLUME_THRESHOLD_PROPERTY = DynamicPropertyFactory.getInstance()
			.getIntProperty(CIRCUIT_REQUEST_VOLUME_THRESHOLD, 10);

	public static DynamicStringProperty EUREKA_SERVER_URL_PROPERTY = DynamicPropertyFactory.getInstance()
			.getStringProperty(EUREKA_SERVER_URL, "http://localhost:8080");

	public static DynamicStringProperty HOST_NAME_PROPERTY = DynamicPropertyFactory.getInstance()
			.getStringProperty(HOST_NAME, "ID_SERVER_00001");

	public static DynamicStringProperty APP_NAME_PORPERTY = DynamicPropertyFactory.getInstance()
			.getStringProperty(APP_NAME, "school-web-service");

	public static DynamicStringProperty IP_ADDRESS_PROPERTY = DynamicPropertyFactory.getInstance()
			.getStringProperty(IP_ADDRESS, "0.0.0.0");

	public static DynamicStringProperty PORT_PROPERTY = DynamicPropertyFactory.getInstance().getStringProperty(PORT,
			"9050");

	public static DynamicStringProperty HOST_URL_PROPERTY = DynamicPropertyFactory.getInstance()
			.getStringProperty(HOST_URL, "http://localhost:9050");

	public static DynamicStringProperty MONGO_HOST_PROPERTY = DynamicPropertyFactory.getInstance()
			.getStringProperty(MONGO_HOST, "localhost");

	public static DynamicIntProperty MONGO_PORT_PROPERTY = DynamicPropertyFactory.getInstance()
			.getIntProperty(MONGO_PORT, 27017);

	public static DynamicStringProperty MONGO_DB_PROPERTY = DynamicPropertyFactory.getInstance()
			.getStringProperty(MONGO_DB, "ideacrest_sdb");
}
