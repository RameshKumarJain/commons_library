package com.ideacrest.app.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EurekaClientApplicationResponse {

	String name;

	List<EurekaInstance> instance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EurekaInstance> getInstance() {
		return instance;
	}

	public void setInstance(List<EurekaInstance> instance) {
		this.instance = instance;
	}

}
