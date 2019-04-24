package com.ideacrest.app.bean;

import java.util.List;
import java.util.Map;

import graphql.GraphQLError;

public class GraphQLResponse {

	private Map<String, Object> data;
	private List<GraphQLError> errors;
	private transient boolean dataPresent;
	private transient Map<Object, Object> extensions;

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public List<GraphQLError> getErrors() {
		return errors;
	}

	public void setErrors(List<GraphQLError> errors) {
		this.errors = errors;
	}

	public boolean isDataPresent() {
		return dataPresent;
	}

	public void setDataPresent(boolean dataPresent) {
		this.dataPresent = dataPresent;
	}

	public Map<Object, Object> getExtensions() {
		return extensions;
	}

	public void setExtensions(Map<Object, Object> extensions) {
		this.extensions = extensions;
	}

}
