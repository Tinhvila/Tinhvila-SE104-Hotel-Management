package com.HotelManagement.Entity;

public class CountCustomerRoomBill {
	private String roomBillId;
	private int countCustomer;
	public CountCustomerRoomBill(String roomBillId, int countCustomer) {
		super();
		this.roomBillId = roomBillId;
		this.countCustomer = countCustomer;
	}
	public CountCustomerRoomBill() {
		
	}
	public String getRoomBillId() {
		return roomBillId;
	}
	public void setRoomBillId(String roomBillId) {
		this.roomBillId = roomBillId;
	}
	public int getCountCustomer() {
		return countCustomer;
	}
	public void setCountCustomer(int countCustomer) {
		this.countCustomer = countCustomer;
	}
	
}
