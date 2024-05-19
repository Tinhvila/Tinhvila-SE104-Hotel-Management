package com.HotelManagement.Entity;

public class TypeCustomer {
	private String typeCustomerId;
	private String typeCustomerName;
	private float typeCustomerChargeRate;
	public String getTypeCustomerId() {
		return typeCustomerId;
	}
	public void setTypeCustomerId(String typeCustomerId) {
		this.typeCustomerId = typeCustomerId;
	}
	public String getTypeCustomerName() {
		return typeCustomerName;
	}
	public void setTypeCustomerName(String typeCustomerName) {
		this.typeCustomerName = typeCustomerName;
	}
	public float getTypeCustomerChargeRate() {
		return typeCustomerChargeRate;
	}
	public void setTypeCustomerChargeRate(float typeCustomerChargeRate) {
		this.typeCustomerChargeRate = typeCustomerChargeRate;
	}
	public TypeCustomer(String typeCustomerId, String typeCustomerName, float typeCustomerChargeRate) {
		super();
		this.typeCustomerId = typeCustomerId;
		this.typeCustomerName = typeCustomerName;
		this.typeCustomerChargeRate = typeCustomerChargeRate;
	}
	public TypeCustomer() {
		
	}
}
