package com.HotelManagement.Entity;

public class TypeCustomerTag {
	private String typeCustomerId;
	private int typeCustomerIsChecked;
	public TypeCustomerTag() {
		super();
	}
	public TypeCustomerTag(String typeCustomerId, int typeCustomerIsChecked) {
		super();
		this.typeCustomerId = typeCustomerId;
		this.typeCustomerIsChecked = typeCustomerIsChecked;
	}
	public String getTypeCustomerId() {
		return typeCustomerId;
	}
	public void setTypeCustomerId(String typeCustomerId) {
		this.typeCustomerId = typeCustomerId;
	}
	public int getTypeCustomerIsChecked() {
		return typeCustomerIsChecked;
	}
	public void setTypeCustomerIsChecked(int typeCustomerIsChecked) {
		this.typeCustomerIsChecked = typeCustomerIsChecked;
	}
}
