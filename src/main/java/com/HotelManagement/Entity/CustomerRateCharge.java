package com.HotelManagement.Entity;

public class CustomerRateCharge {
	private String customerId;
	private String typeCustomerId;
	private float customerChargeRate;
	public CustomerRateCharge() {
		super();
	}
	public CustomerRateCharge(String customerId, String typeCustomerId, float customerChargeRate) {
		super();
		this.customerId = customerId;
		this.typeCustomerId = typeCustomerId;
		this.customerChargeRate = customerChargeRate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTypeCustomerId() {
		return typeCustomerId;
	}
	public void setTypeCustomerId(String typeCustomerId) {
		this.typeCustomerId = typeCustomerId;
	}
	public float getCustomerChargeRate() {
		return customerChargeRate;
	}
	public void setCustomerChargeRate(float customerChargeRate) {
		this.customerChargeRate = customerChargeRate;
	}
}
