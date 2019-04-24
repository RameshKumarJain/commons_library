package com.ideacrest.app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EurekaGetClientInstanceResponse {

	EurekaClientApplicationResponse application;

	public EurekaClientApplicationResponse getApplication() {
		return application;
	}

	public void setApplication(EurekaClientApplicationResponse application) {
		this.application = application;
	}

}
