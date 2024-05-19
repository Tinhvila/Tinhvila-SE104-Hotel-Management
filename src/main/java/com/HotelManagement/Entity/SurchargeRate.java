package com.HotelManagement.Entity;

public class SurchargeRate {
	private int orderOfCustomer;
	private float value;
	public int getOrderOfCustomer() {
		return orderOfCustomer;
	}
	public void setOrderOfCustomer(int orderOfCustomer) {
		this.orderOfCustomer = orderOfCustomer;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public SurchargeRate(int orderOfCustomer, float value) {
		this.orderOfCustomer = orderOfCustomer;
		this.value = value;
	}
	@Override
	public String toString() {
		return "SurchargeRate [orderOfCustomer=" + orderOfCustomer + ", value=" + value + "]";
	}
	
	
	
	
}
