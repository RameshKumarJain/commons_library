package com.ideacrest.app.bean;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EurekaInstance {
	
	private String hostName;
	
	private String app;
	
	private String vipAddress;
	
	private String secureVipAddress;
	
	private String ipAddr;
	
	private String status;
	
	Map<String, String> port;
	
	Map<String, String> securePort;
	
	private String healthCheckUrl;
	
	private String statusPageUrl;
	
	private String homePageUrl;
	
	Map<String, String> dataCenterInfo;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getVipAddress() {
		return vipAddress;
	}

	public void setVipAddress(String vipAddress) {
		this.vipAddress = vipAddress;
	}

	public String getSecureVipAddress() {
		return secureVipAddress;
	}

	public void setSecureVipAddress(String secureVipAddress) {
		this.secureVipAddress = secureVipAddress;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getPort() {
		return port;
	}

	public void setPort(Map<String, String> port) {
		this.port = port;
	}

	public Map<String, String> getSecurePort() {
		return securePort;
	}

	public void setSecurePort(Map<String, String> securePort) {
		this.securePort = securePort;
	}

	public String getHealthCheckUrl() {
		return healthCheckUrl;
	}

	public void setHealthCheckUrl(String healthCheckUrl) {
		this.healthCheckUrl = healthCheckUrl;
	}

	public String getStatusPageUrl() {
		return statusPageUrl;
	}

	public void setStatusPageUrl(String statusPageUrl) {
		this.statusPageUrl = statusPageUrl;
	}

	public String getHomePageUrl() {
		return homePageUrl;
	}

	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}

	public Map<String, String> getDataCenterInfo() {
		return dataCenterInfo;
	}

	public void setDataCenterInfo(Map<String, String> dataCenterInfo) {
		this.dataCenterInfo = dataCenterInfo;
	}
}
