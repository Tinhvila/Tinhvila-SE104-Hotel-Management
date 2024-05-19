package com.HotelManagement.Entity;

public class Parameter {
	private String paramName;
	private float paramValue;
	
	public Parameter(String paramName, float paramValue) {
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public float getParamValue() {
		return paramValue;
	}
	public void setParamValue(float paramValue) {
		this.paramValue = paramValue;
	}
	@Override
	public String toString() {
		return "Parameter [paramName=" + paramName + ", paramValue=" + paramValue + "]";
	}
	
	
}
