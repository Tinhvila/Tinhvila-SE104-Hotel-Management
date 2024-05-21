package com.HotelManagement.Entity;

public class CountCustomerRoomBill {
	private String roomBillId;
	private int countCustomer;
	private float initialPrice;
	public float getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(float initialPrice) {
		this.initialPrice = initialPrice;
	}
	public CountCustomerRoomBill(String roomBillId, int countCustomer, float initialPrice) {
		super();
		this.roomBillId = roomBillId;
		this.countCustomer = countCustomer;
		this.initialPrice = initialPrice;
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
