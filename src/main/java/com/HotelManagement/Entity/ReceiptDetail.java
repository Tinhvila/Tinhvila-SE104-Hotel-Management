package com.HotelManagement.Entity;

public class ReceiptDetail {
	private String receiptDetailId;
	private String receiptId;
	private RoomBill receiptRoomBillInfo;
	private int countRoomDayRent;
	private float receiptTotalValue;
	public String getReceiptDetailId() {
		return receiptDetailId;
	}
	public void setReceiptDetailId(String receiptDetailId) {
		this.receiptDetailId = receiptDetailId;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public RoomBill getReceiptRoomBillInfo() {
		return receiptRoomBillInfo;
	}
	public void setReceiptRoomBillInfo(RoomBill receiptRoomBillInfo) {
		this.receiptRoomBillInfo = receiptRoomBillInfo;
	}
	public int getCountRoomDayRent() {
		return countRoomDayRent;
	}
	public void setCountRoomDayRent(int countRoomDayRent) {
		this.countRoomDayRent = countRoomDayRent;
	}
	public float getReceiptTotalValue() {
		return receiptTotalValue;
	}
	public void setReceiptTotalValue(float receiptTotalValue) {
		this.receiptTotalValue = receiptTotalValue;
	}
	public ReceiptDetail() {
		
	}
	
}
