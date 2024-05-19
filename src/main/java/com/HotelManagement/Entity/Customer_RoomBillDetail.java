package com.HotelManagement.Entity;

public class Customer_RoomBillDetail {
	private String customerId;
	private String roomBillId;
	private String customerName;
	private String customerAddress;
	private String customerIdentityCode;
	private String typeCustomerId;
	
	public Customer_RoomBillDetail() {
		super();
	}
	public Customer_RoomBillDetail(String customerId, String roomBillId, String customerName, String customerAddress,
			String customerIdentityCode, String typeCustomerId) {
		super();
		this.customerId = customerId;
		this.roomBillId = roomBillId;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerIdentityCode = customerIdentityCode;
		this.typeCustomerId = typeCustomerId;
	}
	@Override
	public String toString() {
		return "Customer_RoomBillDetail [customerId=" + customerId + ", roomBillId=" + roomBillId + ", customerName="
				+ customerName + ", customerAddress=" + customerAddress + ", customerIdentityCode="
				+ customerIdentityCode + ", typeCustomerId=" + typeCustomerId + "]";
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getRoomBillId() {
		return roomBillId;
	}
	public void setRoomBillId(String roomBillId) {
		this.roomBillId = roomBillId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerIdentityCode() {
		return customerIdentityCode;
	}
	public void setCustomerIdentityCode(String customerIdentityCode) {
		this.customerIdentityCode = customerIdentityCode;
	}
	public String getTypeCustomerId() {
		return typeCustomerId;
	}
	public void setTypeCustomerId(String typeCustomerId) {
		this.typeCustomerId = typeCustomerId;
	}
	
	
}
